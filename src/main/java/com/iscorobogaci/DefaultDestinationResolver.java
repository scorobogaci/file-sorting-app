package com.iscorobogaci;

import com.iscorobogaci.enums.DestinationFolder;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public class DefaultDestinationResolver extends DestinationResolver {

    protected DefaultDestinationResolver(Path rootDirectory) {
        super(rootDirectory);
    }

    @Override
    protected Path destinationPath(Path file) {
        return Paths.get(DestinationFolder.NOT_SORTED.value().concat(File.separator));
    }

}
