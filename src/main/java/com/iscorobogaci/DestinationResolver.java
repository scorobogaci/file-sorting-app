package com.iscorobogaci;

import com.drew.imaging.ImageProcessingException;

import java.io.IOException;
import java.nio.file.Path;

import static java.util.Objects.nonNull;

abstract class DestinationResolver {

    private final Path rootDirectory;

    protected DestinationResolver(Path rootDirectory) {
        this.rootDirectory = rootDirectory;
    }

    protected abstract Path destinationPath(Path path) throws ImageProcessingException, IOException;

    public final Path resolveDestinationPath(Path path) throws ImageProcessingException, IOException {
        Path relativePath = destinationPath(path);
        if (nonNull(relativePath)) {
            return rootDirectory.resolve(relativePath);
        }
        return null;
    }

}
