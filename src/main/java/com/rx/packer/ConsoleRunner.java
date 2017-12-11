package com.rx.packer;

import com.rx.packer.builders.Builder;
import com.rx.packer.builders.SetBuilder;
import com.rx.packer.datamodel.Set;
import com.rx.packer.utils.FileRxBasedPublisher;
import com.rx.packer.utils.PackerHelper;
import com.rx.packer.utils.Parameters;
import org.apache.log4j.Logger;

/**
 * Responsible for running the packer app (the process of packaging from console)
 * <p>
 * Created by RLYBD20 on 5/12/2017.
 */
public class ConsoleRunner {

    private static Logger LOG = Logger.getLogger(ConsoleRunner.class);

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        final Builder<String, Set> setBuilder = new SetBuilder();
        final PackerHelper packerHelper = new PackerHelper();

        Parameters.init("application.properties");

        Packer packer = new Packer(setBuilder, packerHelper, new FileRxBasedPublisher(Parameters.getFileNameWithSets()));
        // custom subscriber might be plugged below.
        packer.pack().blockingSubscribe();

        long endTime = System.currentTimeMillis();
        long totalTime = endTime - startTime;
        LOG.info("Total time of packaging : " + totalTime + " ms.");
    }
}
