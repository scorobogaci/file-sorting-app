package com.iscorobogaci;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Metadata;
import com.drew.metadata.mov.QuickTimeDirectory;
import com.drew.metadata.mp4.Mp4Directory;
import com.iscorobogaci.enums.DestinationFolder;
import com.iscorobogaci.enums.SortingBy;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;

import static java.time.ZoneId.systemDefault;
import static java.util.Objects.nonNull;
import static java.util.TimeZone.getDefault;

public class VideoDestinationResolver extends DestinationResolver {

    private RootPathResolver rootPathResolver;
    private SortingBy sortingBy;

    public VideoDestinationResolver(Path rootDirectory, SortingBy sortingBy) {
        super(rootDirectory);
        this.rootPathResolver = new RootPathResolver();
        this.sortingBy = sortingBy;
    }

    @Override
    protected Path destinationPath(Path path) throws ImageProcessingException, IOException {
        LocalDate fileCreationDate = readFileCreationDate(path);
        if (nonNull(fileCreationDate)) {
            String rootForPath = rootPathResolver.resolveRootPath(fileCreationDate, sortingBy);
            return Paths.get(DestinationFolder.VIDEO.value()
                    .concat(File.separator)
                    .concat(rootForPath));
        }
        return null;
    }

    private LocalDate readFileCreationDate(Path path) throws ImageProcessingException, IOException {
        Metadata fileMetadata = ImageMetadataReader.readMetadata(path.toFile());

        Mp4Directory mp4Directory = fileMetadata.getFirstDirectoryOfType(Mp4Directory.class);
        QuickTimeDirectory quickTimeDirectory = fileMetadata.getFirstDirectoryOfType(QuickTimeDirectory.class);

        if (nonNull(mp4Directory)) {
            return mp4Directory.getDate(Mp4Directory.TAG_CREATION_TIME, getDefault()).toInstant().atZone(systemDefault()).toLocalDate();
        } else if (nonNull(quickTimeDirectory)) {
            return quickTimeDirectory.getDate(QuickTimeDirectory.TAG_CREATION_TIME, getDefault()).toInstant().atZone(systemDefault()).toLocalDate();
        }
        return null;
    }

}
