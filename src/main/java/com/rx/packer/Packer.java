package com.rx.packer;

import com.rx.packer.builders.Builder;
import com.rx.packer.builders.NiceItemsMessageBuilder;
import com.rx.packer.datamodel.Item;
import com.rx.packer.datamodel.Set;
import com.rx.packer.utils.PackerHelper;
import com.rx.packer.utils.Parameters;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableOnSubscribe;
import org.apache.log4j.Logger;

import java.util.List;

/**
 * Entry point of an application.
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

    public void execute() {
        LOGGER.info("Start packaging things ...");
        final Builder<List<List<Item>>, String> niceItemsMessageBuilder = new NiceItemsMessageBuilder();

        Flowable
                .defer(() -> Flowable.create(publisher, BackpressureStrategy.BUFFER))
                .doOnNext(s -> LOGGER.debug("Operating on : " + s))
                // 1. Building internal app objects based on the specified file.
                .flatMap(inputLine -> Flowable
                        .just(inputLine)
                        //.subscribeOn(Schedulers.io())
                        .filter(builder::validate)
                        .map(builder::build)
                        .doOnNext(p -> LOGGER.debug("Internal set has been successfully built : " + p))
                        // swallowing all errors by just skipping the input line for processing.
                        .onErrorResumeNext(throwable -> {
                            LOGGER.error("Skipping the given set due to it was damaged.");
                            return Flowable.empty();
                        }))
                .flatMap(set -> Flowable
                                .fromIterable(set.getItems())
                                .filter(item -> item.getWeight() <= set.getWeightPackageCanTake())
                                .filter(item -> item.getCost() <= Parameters.getMaxItemCost())
                                .doOnNext(item -> LOGGER.debug(item + " passed the basic validation."))
                                .toList()
                                .toFlowable()
                                .map(helper::generateCombinations)
                                .doOnNext(lists -> LOGGER.trace("Printing out all combinations to be considered for packaging. " + niceItemsMessageBuilder.build(lists)))
                        //.zipWith(Flowable.just(set.getWeightPackageCanTake()), (s, w) -> new Set(w, s))
                )
                .subscribe();
    }
}
