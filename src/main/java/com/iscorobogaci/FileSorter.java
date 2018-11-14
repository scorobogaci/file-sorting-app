package com.iscorobogaci;

import com.drew.imaging.ImageProcessingException;
import com.iscorobogaci.enums.PatternMatcher;
import com.iscorobogaci.enums.SortingBy;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.util.stream.Stream;

import static java.nio.file.FileSystems.getDefault;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import static java.util.Objects.nonNull;

public class FileSorter {

    private Path sourceRootDirectory;
    private Path destinationRootDirectory;
    private SortingBy sortingBy;


    public FileSorter(Path sourceRootDirectory, Path destinationRootDirectory, SortingBy sortingBy) {
        this.sourceRootDirectory = sourceRootDirectory;
        this.destinationRootDirectory = destinationRootDirectory;
        this.sortingBy = sortingBy;
    }

    //used only in tests at the moment
    public void copy() throws IOException {
        Stream<Path> sourceFiles = Files.list(sourceRootDirectory);

        sourceFiles
                .filter(path -> Files.isRegularFile(path))
                .forEach(sourcePath -> {
                    try {
                        performCopy(sourcePath);
                    } catch (ImageProcessingException e) {
                        e.printStackTrace();
                        FileExceptionHandler.getInstance().logException(e);
                    }
                });
    }

    public void performCopy(Path sourcePath) throws ImageProcessingException {
        try {
            DestinationResolver destinationResolver;
            PathMatcher imagePathMatcher = getDefault().getPathMatcher(PatternMatcher.IMAGE.value());
            PathMatcher videoPathMatcher = getDefault().getPathMatcher(PatternMatcher.VIDEO.value());
            if (imagePathMatcher.matches(sourcePath)) {
                destinationResolver = new ImageDestinationResolver(destinationRootDirectory, sortingBy);
            } else if (videoPathMatcher.matches(sourcePath)) {
                destinationResolver = new VideoDestinationResolver(destinationRootDirectory, sortingBy);
            } else {
                destinationResolver = new DefaultDestinationResolver(destinationRootDirectory);
            }

            Path destinationForSource = destinationResolver.resolveDestinationPath(sourcePath);
            if (nonNull(destinationForSource)) {
                createDestinationPath(destinationForSource);
                Files.copy(sourcePath, destinationForSource.resolve(sourcePath.getFileName()), REPLACE_EXISTING);
            }

        } catch (IOException e) {
            FileExceptionHandler.getInstance().logException(e);
        }
    }

    private void createDestinationPath(Path destinationPath) throws IOException {
        if (!Files.exists(destinationPath)) {
            Files.createDirectories(destinationPath);
        }
    }
}

