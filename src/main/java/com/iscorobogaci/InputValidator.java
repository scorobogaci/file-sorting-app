package com.iscorobogaci;

import com.iscorobogaci.enums.SortingBy;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class InputValidator {

    public void validateProvidedArguments(String firstArg, String secondArg, String thirdArg) {
        Path sourcePath = Paths.get(firstArg);
        Path destinationPath = Paths.get(secondArg);
        try {
            SortingBy.valueOf(thirdArg);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(ClientMessage.INVALID_SORTING_BY_ARGUMENT.concat(thirdArg).concat(ClientMessage.SORTING_BY_VALID_FORMAT));
        }
        if (!Files.isDirectory(sourcePath)) {
            throw new IllegalArgumentException(ClientMessage.INVALID_SOURCE_PATH_MESSAGE.concat(sourcePath.toString()).concat(ClientMessage.PATH_FORMAT));
        }
        if (!Files.isDirectory(destinationPath)) {
            throw new IllegalArgumentException(ClientMessage.INVALID_DESTINATION_PATH_MESSAGE.concat(destinationPath.toString()).concat(ClientMessage.PATH_FORMAT));
        }
    }
}

