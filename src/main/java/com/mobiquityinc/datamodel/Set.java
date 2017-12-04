package com.mobiquityinc.datamodel;

import java.util.List;

/**
 * Package represents one row of the txt file -> a set of things to be placed within the package.
 */
public final class Set {

    private Integer weightPackageCanTake;
    private List<Item> items;

    public Set(Integer weightPackageCanTake, List<Item> items) {
        this.weightPackageCanTake = weightPackageCanTake;
        this.items = items;
    }

    public Integer getWeightPackageCanTake() {
        return weightPackageCanTake;
    }

    public List<Item> getItems() {
        return items;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Set)) return false;

        Set aPackage = (Set) o;

        if (!getWeightPackageCanTake().equals(aPackage.getWeightPackageCanTake()))
            return false;
        return getItems().equals(aPackage.getItems());
    }

    // given hashcode implementation could get changed.
    @Override
    public int hashCode() {
        int result = getWeightPackageCanTake().hashCode();
        result = 31 * result + getItems().hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Package{" +
                "weightPackageCanTake=" + weightPackageCanTake +
                ", items=" + items +
                '}';
    }
}
