package com.iscorobogaci;

import com.iscorobogaci.enums.SortingBy;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.iscorobogaci.ClientMessage.*;

public class MainApplication {

    private static final Logger CONSOLE_LOGGER = Logger.getLogger(MainApplication.class.getName());

    public static void main(String[] args) {
        CONSOLE_LOGGER.info(BRAND_LOGO);
        validateProgramArguments(args);
        validateInput(args[0], args[1], args[2]);
        CONSOLE_LOGGER.info(GENERATION_IN_PROGRESS_MESSAGE);
        Path sourcePath = Paths.get(args[0]);
        Path destinationPath = Paths.get(args[1]);
        SortingBy sortingBy = SortingBy.valueOf(args[2]);
        performSort(sourcePath, destinationPath, sortingBy);
        openDestinationDirectory(new File(destinationPath.toString()));
        CONSOLE_LOGGER.info(FINISH_CLIENT_MESSAGE.concat(Quotes.getRandomQuote()));
    }

    private static void validateProgramArguments(String[] args) {
        if (args.length != 3) {
            CONSOLE_LOGGER.log(Level.SEVERE, WRONG_ARGUMENTS_PROVISIONING.concat(PROPER_ARGUMENT_USAGE_MESSAGE));
            System.exit(1);
        }
    }

    private static void validateInput(String firstArg, String secondArg, String thirdArg) {
        InputValidator inputValidator = new InputValidator();
        try {
            inputValidator.validateProvidedArguments(firstArg, secondArg, thirdArg);
        } catch (IllegalArgumentException e) {
            FileExceptionHandler.getInstance().logException(e);
            CONSOLE_LOGGER.log(Level.SEVERE, e.getMessage());
            System.exit(1);
        }
    }

    private static void performSort(Path sourcePath, Path destinationPath, SortingBy sortingBy) {
        FileSorter fileSorter = new FileSorter(sourcePath, destinationPath,sortingBy);
        try {
            fileSorter.copy();
        } catch (IOException e) {
            FileExceptionHandler.getInstance().logException(e);
        }
    }

    private static void openDestinationDirectory(File destinationDirectory) {
        try {
            Desktop.getDesktop().open(destinationDirectory);
        } catch (IOException e) {
            FileExceptionHandler.getInstance().logException(e);
        }
    }
}
