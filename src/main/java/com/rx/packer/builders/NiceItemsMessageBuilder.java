package com.rx.packer.builders;

import com.rx.packer.datamodel.Item;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.List;

/**
 * Builds a nice message of items to be packaged it can be output on console.
 * <p>
 * Created by RLYBD20 on 6/12/2017.
 */
public class NiceItemsMessageBuilder implements Builder<List<List<Item>>, String> {

    @Override
    public String build(List<List<Item>> lists) {
        final StringBuilder result = new StringBuilder().append("\n");
        result.append("[").append("\n");

        lists.forEach(items -> {
            result.append("\t").append(items.toString()).append("\n");
        });

        result.append("]");
        return result.toString();
    }

    @Override
    public boolean validate(List<List<Item>> inputItem) {
        throw new NotImplementedException();
    }
}
