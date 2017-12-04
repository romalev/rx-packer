package com.mobiquityinc.builders;

import com.mobiquityinc.datamodel.Item;
import com.mobiquityinc.datamodel.Set;
import com.mobiquityinc.exception.APIException;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SetBuilder implements Builder<String, Set> {

    private static final Logger LOGGER = Logger.getLogger(SetBuilder.class);

    public boolean validate(String inputLine) {
        // TODO : implement validation !!!
        return true;
    }

    /**
     * Builds a package based on the supplied input.
     */
    public Set build(String inputLine) {
        LOGGER.debug("Building a package out of : " + inputLine);
        // 1. Splitting the input line by : -> should get 2 pieces.
        String[] parts = inputLine.split(":");
        if (parts.length != 2 || StringUtils.isEmpty(parts[0]) || StringUtils.isEmpty(parts[1])) {
            throw new APIException("Can't build a package, parsing has failed for : " + inputLine);
        }

        // 2. Applying regex to parse the rest of the package.
        final Pattern p = Pattern.compile("(\\d+)\\,(\\d+\\.?\\d*)\\,(\\D\\d+)");
        final Matcher m = p.matcher(parts[1]);

        // TODO: consider using eclipse collection here.
        List<Item> items = new LinkedList<>();

        while (m.find()) {
            Item item = new Item();
            item.setIndexNumber(Integer.valueOf(m.group(1)));
            item.setWeight(Double.valueOf(m.group(2)));
            item.setCost(Double.valueOf(m.group(3).substring(1)));
            items.add(item);
        }
        return new Set(Integer.valueOf(parts[0].trim()), items);
    }

}
