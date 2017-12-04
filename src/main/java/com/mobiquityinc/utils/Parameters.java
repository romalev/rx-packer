package com.mobiquityinc.utils;

import org.apache.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Contains simple logic to read the input parameters from configuration file.
 */
public final class Parameters {

    private static final Logger logger = Logger.getLogger(Parameters.class);

    private static final String PROPERTY_FILE_NAME = "application.properties";

    private static int maxPackageWeight;
    private static int maxItemWeight;
    private static int maxItemCost;
    private static int maxPackageSize;
    private static String fileNameWithSets;

    public static void init() {
        logger.info("Initializing application properties " + PROPERTY_FILE_NAME + ".");
        final Properties properties = new Properties();
        try {
            properties.load(new FileInputStream(Parameters.class.getClassLoader().getResource(PROPERTY_FILE_NAME).getFile()));
        } catch (IOException e) {
            logger.error("Error occurred while reading property file: " + PROPERTY_FILE_NAME);
        }

        maxPackageWeight = Integer.parseInt(properties.getProperty("max.package.weight"));
        maxItemWeight = Integer.parseInt(properties.getProperty("max.item.weight"));
        maxItemCost = Integer.parseInt(properties.getProperty("max.item.cost"));
        maxPackageSize = Integer.parseInt(properties.getProperty("max.package.size"));
        fileNameWithSets = properties.getProperty("fileName.with.input.sets");

        logger.info("Properties have been successfully read.");
    }

    public static int getMaxPackageWeight() {
        return maxPackageWeight;
    }

    public static int getMaxItemWeight() {
        return maxItemWeight;
    }

    public static int getMaxItemCost() {
        return maxItemCost;
    }

    public static int getMaxPackageSize() {
        return maxPackageSize;
    }

    public static String getFileNameWithSets() {
        return fileNameWithSets;
    }
}
