package com.rx.packer.datamodel;

/**
 * Created by RLYBD20 on 15/09/2017.
 */
public final class Item {

    private Integer indexNumber;
    private Double weight;
    private Double cost;

    public Item() {
    }

    public Item(Integer indexNumber, Double weight, Double cost) {
        this.indexNumber = indexNumber;
        this.weight = weight;
        this.cost = cost;
    }

    public Integer getIndexNumber() {
        return indexNumber;
    }

    public Double getWeight() {
        return weight;
    }

    public Double getCost() {
        return cost;
    }

    public void setIndexNumber(Integer indexNumber) {
        this.indexNumber = indexNumber;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Item)) return false;

        Item item = (Item) o;

        return getIndexNumber().equals(item.getIndexNumber());
    }

    @Override
    public int hashCode() {
        return getIndexNumber().hashCode();
    }

    @Override
    public String toString() {
        return "Item{" +
                "indexNumber=" + indexNumber +
                ", weight=" + weight +
                ", cost=" + cost +
                '}';
    }
}
