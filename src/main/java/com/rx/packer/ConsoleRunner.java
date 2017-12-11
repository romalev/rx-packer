package com.rx.packer;

import com.rx.packer.builders.Builder;
import com.rx.packer.builders.SetBuilder;
import com.rx.packer.datamodel.Set;
import com.rx.packer.utils.FileRxBasedPublisher;
import com.rx.packer.utils.PackerHelper;
import com.rx.packer.utils.Parameters;

/**
 * Responsible for running the packer app (the process of packaging from console)
 * <p>
 * Created by RLYBD20 on 5/12/2017.
 */
public class ConsoleRunner {

    public static void main(String[] args) {
        final Builder<String, Set> setBuilder = new SetBuilder();
        final PackerHelper packerHelper = new PackerHelper();

        Parameters.init("application.properties");

        Packer packer = new Packer(setBuilder, packerHelper, new FileRxBasedPublisher(Parameters.getFileNameWithSets()));
        // custom subscriber might be plugged below.
        packer.pack().blockingSubscribe();
    }

}
