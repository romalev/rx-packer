package com.rx.packer.utils;

import com.rx.packer.datamodel.Item;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Helps packaging things into packages.
 * <p>
 * Created by RLYBD20 on 1/11/2017.
 */
public class PackerHelper {

    private Logger LOGGER = Logger.getLogger(PackerHelper.class);

    /**
     * Generates all possible unique combinations of the items within item list.
     * Later on combinations are going to be looked up by special algorithm and best options will be picked and packaged.
     * <p/>
     * Example: given list : 1, 2, 3 -> output: [[1], [1,2], [2], [1,3], [1,2,3], [2,3], [3]]
     * <p/>
     * Just a note : this is IMHO the most complicated part of the task.
     *
     * @param itemList holds all items (ones that exist withing a set.)
     * @return all possible combinations of items.
     */
    public List<List<Item>> generateCombinations(final List<Item> itemList) {
        final List<List<Item>> allPossibleCombinations = new ArrayList<>();
        itemList.forEach(item -> {
            List<List<Item>> generatedCombinations = new ArrayList<>();
            allPossibleCombinations.forEach(list -> {
                List<Item> newCombination = new ArrayList<>(list);
                newCombination.add(item);
                generatedCombinations.add(newCombination);
            });
            allPossibleCombinations.addAll(generatedCombinations);
            allPossibleCombinations.add(Collections.singletonList(item));
        });
        return allPossibleCombinations;
    }

    /**
     * Looks up items list by applying special algorithm described below and based on that finds beast options for packaging.
     * <p/>
     * Algorithm : Goal is to determine which things to put into the package so that the total weight is less than or equal to the package limit and
     * the total cost is as large as possible. Preferable option is to send a package which weights less in case
     * there is more than one package with the same price.
     *
     * @param allPossibleOptions holds all possible options of items for packaging.
     * @param packageWeightLimit holds allowed package weight limit.
     * @return package ready for delivering.
     */
    public List<Item> lookUpBestOptionsForPackaging(final List<List<Item>> allPossibleOptions,
                                                    final Integer packageWeightLimit) {
        double bestWeight = 0;
        double bestCost = 0;
        List<Item> bestOption = new ArrayList<>();

        for (final List<Item> itemList : allPossibleOptions) {
            double combinationTotalWeight = itemList.stream().mapToDouble(Item::getWeight).sum();
            if (combinationTotalWeight <= packageWeightLimit) {
                // item list is to be considered for packaging.
                double combinationTotalCost = itemList.stream().mapToDouble(Item::getCost).sum();
                if (bestCost < combinationTotalCost) {
                    bestWeight = combinationTotalWeight;
                    bestCost = combinationTotalCost;
                    bestOption = itemList;
                }
                if (bestCost == combinationTotalCost) {
                    if (combinationTotalWeight < bestWeight) {
                        bestWeight = combinationTotalWeight;
                        bestOption = itemList;
                    }
                }
            }
        }
        return bestOption;
    }
}
