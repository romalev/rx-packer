package com.rx.packer.utils;

import com.rx.packer.datamodel.Item;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Contains helper - like methods needed for Packer application.
 * <p>
 * Created by RLYBD20 on 1/11/2017.
 */
public class PackerHelper {

    /**
     * Generates all possible unique combinations of the elements of the list.
     * Example: given list : 1, 2, 3 -> output: [[1], [1,2], [2], [1,3], [1,2,3], [2,3], [3]]
     */
    public List<List<Item>> generateCombinations(final List<Item> givenList) {
        final List<List<Item>> allPossibleCombinations = new ArrayList<>();
        givenList.forEach(item -> {
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
}
