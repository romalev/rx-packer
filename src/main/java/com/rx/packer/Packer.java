package com.rx.packer;

import com.rx.packer.builders.Builder;
import com.rx.packer.builders.ItemsMessageBuilder;
import com.rx.packer.datamodel.Item;
import com.rx.packer.datamodel.Set;
import com.rx.packer.utils.PackerHelper;
import com.rx.packer.utils.Parameters;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableOnSubscribe;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Things are being packaged here.
 * <p>
 * Created by RLYBD20 on 15/09/2017.
 */
public class Packer {

    private static final Logger LOGGER = Logger.getLogger(Packer.class);

    private FlowableOnSubscribe<String> publisher; // publishes things for packaging.
    private Builder<String, Set> builder; // build things that are produced by publisher.
    private PackerHelper helper; // helps packaging things.

    public Packer(final Builder<String, Set> builder, final PackerHelper helper, final FlowableOnSubscribe<String> publisher) {
        this.builder = builder;
        this.helper = helper;
        this.publisher = publisher;
    }

    public Flowable<List<Integer>> pack() {
        LOGGER.info("Start packaging things ...");
        final Builder<List<List<Item>>, String> niceItemsMessageBuilder = new ItemsMessageBuilder();

        return Flowable
                .defer(() -> Flowable.create(publisher, BackpressureStrategy.BUFFER))
                //.observeOn(Schedulers.io())
                .doOnNext(s -> LOGGER.debug("Operating on : " + s))
                // 1. Building internal app objects based on the specified file.
                .flatMap(inputLine -> Flowable
                        .just(inputLine)
                        // how about introducing a validation here ???
                        .map(builder::build)
                        .doOnNext(p -> LOGGER.debug("Internal set has been successfully built : " + p))
                        // swallowing all errors by just skipping the input line for processing.
                        .onErrorResumeNext(throwable -> {
                            LOGGER.error("Skipping the given set due to it was damaged.");
                            return Flowable.empty();
                        }))
                .flatMap(set -> Flowable
                        .fromIterable(set.getItems())
                        .filter(item -> item.getWeight() <= set.getWeightPackageCanTake()) // is this really needed ???
                        .filter(item -> item.getWeight() <= Parameters.getMaxItemWeight())
                        .filter(item -> item.getCost() <= Parameters.getMaxItemCost())
                        .toList()
                        // there might be up to 15 items you need to choose from.
                        .filter(itemList -> itemList.size() < Parameters.getMaxPackageSize())
                        .toFlowable()
                        // generate all possible combination of items
                        //.observeOn(Schedulers.computation())
                        .map(helper::generateCombinations)
                        .doOnNext(lists -> LOGGER.trace("Printing out all combinations to be considered for packaging: " + niceItemsMessageBuilder.build(lists)))
                        .map(lists -> helper.lookUpBestOptionsForPackaging(lists, set.getWeightPackageCanTake()))
                        .doOnNext(itemList -> {
                            StringBuilder message = new StringBuilder();
                            message
                                    .append("\n")
                                    .append("-----------------------------------------------------------------------").append("\n")
                                    .append("For a given set : " + set)
                                    .append("\n")
                                    .append("Given items were packaged : ").append(itemList)
                                    .append("\n")
                                    .append("-----------------------------------------------------------------------").append("\n");
                            LOGGER.trace(message.toString());
                        })
                        .map(packagedItemList -> packagedItemList.stream().map(Item::getIndexNumber).collect(Collectors.toList()))
                        .doOnNext(indexesOfPackagedItems -> LOGGER.info("Printing out only indexes of packaged items: " + indexesOfPackagedItems))
                );

    }
}
