package com.rx.packer.utils;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.Properties;

/**
 * Contains simple logic to read the input parameters from configuration file.
 */
public final class Parameters {

    private static final Logger logger = Logger.getLogger(Parameters.class);

    private static Double maxPackageWeight;
    private static Double maxItemWeight;
    private static Double maxItemCost;
    private static Double maxPackageSize;
    private static String fileNameWithSets;

    public static void init(final String parametersFile) {
        logger.info("Initializing application parameters " + parametersFile + ".");
        final Properties properties = new Properties();
        try {
            properties.load(Parameters.class.getClassLoader().getResourceAsStream(parametersFile));
        } catch (IOException e) {
            logger.error("Error occurred while reading parameters file: " + parametersFile + " Error details are: " + e.getMessage());
        }

        maxPackageWeight = Double.parseDouble(properties.getProperty("max.package.weight"));
        maxItemWeight = Double.parseDouble(properties.getProperty("max.item.weight"));
        maxItemCost = Double.parseDouble(properties.getProperty("max.item.cost"));
        maxPackageSize = Double.parseDouble(properties.getProperty("max.package.size"));
        fileNameWithSets = properties.getProperty("fileName.with.input.sets");

        logger.info("Properties have been successfully read.");
    }

    public static Double getMaxPackageWeight() {
        return maxPackageWeight;
    }

    public static Double getMaxItemWeight() {
        return maxItemWeight;
    }

    public static Double getMaxItemCost() {
        return maxItemCost;
    }

    public static Double getMaxPackageSize() {
        return maxPackageSize;
    }

    public static String getFileNameWithSets() {
        return fileNameWithSets;
    }
}
