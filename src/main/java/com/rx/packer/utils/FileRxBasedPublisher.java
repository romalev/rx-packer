package com.rx.packer.utils;

import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Represents rx-like publisher that publishes given file by emitting line by line (line-> new event) to the
 * executable pipeline.
 */
public class FileRxBasedPublisher implements FlowableOnSubscribe<String> {

    private static final Logger LOGGER = Logger.getLogger(FileRxBasedPublisher.class);

    private final String fileName;

    public FileRxBasedPublisher(final String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void subscribe(FlowableEmitter<String> emitter) throws Exception {
        try {
            Files
                    .lines(getFileNamePath())
                    .forEach(s -> {
                        try {
                            emitter.onNext(s);
                        } catch (Exception e) {
                            emitter.onError(e);
                        }
                    });
            emitter.onComplete();
        } catch (IOException e) {
            LOGGER.error(new StringBuilder()
                    .append("Something went wrong with reading a file: ")
                    .append(fileName)
                    .append("More detailed message: ")
                    .append(e.getMessage()));
            throw e;
        }
    }

    public Path getFileNamePath() {
        return Paths.get(this
                .getClass()
                .getClassLoader()
                .getResource(fileName)
                .getPath()
                // YUP THIS IS NEEDED :(
                .replaceFirst("^/(.:/)", "$1"));
    }
}
