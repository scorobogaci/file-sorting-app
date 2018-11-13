package com.iscorobogaci;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.logging.*;

public class FileExceptionHandler {

    private static final Logger LOGGER = Logger.getLogger(FileExceptionHandler.class.getName());

    private static final LogManager LOG_MANAGER = LogManager.getLogManager();

    private static FileExceptionHandler fileExceptionHandler = null;

    private static final String EXCEPTION_LOG_FILE = "exception.log";

    private FileHandler fileHandler;

    private FileExceptionHandler() {
        SimpleFormatter simpleFormatter = new SimpleFormatter();
        try {
            String loggingConfigurationFile = FileExceptionHandler.class.getResource("/logging.properties").getFile();
            LOG_MANAGER.readConfiguration(new FileInputStream(loggingConfigurationFile));
            fileHandler = new FileHandler(EXCEPTION_LOG_FILE);
            fileHandler.setFormatter(simpleFormatter);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, ConsoleMessage.IO_OPERATIONS_ERROR_MESSAGE);
        }
    }

    public static FileExceptionHandler getInstance() {
        if (fileExceptionHandler == null) {
            fileExceptionHandler = new FileExceptionHandler();
        }
        return fileExceptionHandler;
    }

    public void logException(Exception e) {
        LOGGER.setUseParentHandlers(false);
        LOGGER.addHandler(fileHandler);
        LOGGER.log(Level.SEVERE, e.getMessage(), e);
    }
}
