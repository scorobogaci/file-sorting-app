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
            throw new IllegalArgumentException(ConsoleMessage.INVALID_SORTING_BY_ARGUMENT.concat(thirdArg).concat(ConsoleMessage.SORTING_BY_VALID_FORMAT));
        }
        if (!Files.isDirectory(sourcePath)) {
            throw new IllegalArgumentException(ConsoleMessage.INVALID_SOURCE_PATH_MESSAGE.concat(sourcePath.toString()).concat(ConsoleMessage.PATH_FORMAT));
        }
        if (!Files.isDirectory(destinationPath)) {
            throw new IllegalArgumentException(ConsoleMessage.INVALID_DESTINATION_PATH_MESSAGE.concat(destinationPath.toString()).concat(ConsoleMessage.PATH_FORMAT));
        }
    }
}

