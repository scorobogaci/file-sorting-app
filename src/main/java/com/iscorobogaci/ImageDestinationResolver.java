package com.iscorobogaci;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Metadata;
import com.drew.metadata.exif.ExifSubIFDDirectory;
import com.drew.metadata.xmp.XmpDirectory;
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

public class ImageDestinationResolver extends DestinationResolver {

    private static final String XMP_CREATE_DATE_PROPERTY = "xmp:CreateDate";
    private RootPathResolver rootPathResolver;
    private SortingBy sortingBy;

    protected ImageDestinationResolver(Path rootDirectory, SortingBy sortingBy) {
        super(rootDirectory);
        this.rootPathResolver = new RootPathResolver();
        this.sortingBy = sortingBy;
    }

    @Override
    protected Path destinationPath(Path path) throws ImageProcessingException, IOException {
        LocalDate localDate = readFileCreationDate(path);
        if (nonNull(localDate)) {
            String rootForPath = rootPathResolver.resolveRootPath(localDate, sortingBy);
            return Paths.get(DestinationFolder.PICTURES.value()
                    .concat(File.separator)
                    .concat(rootForPath));
        }
        return null;
    }

    private LocalDate readFileCreationDate(Path path) throws ImageProcessingException, IOException {
        Metadata fileMetadata = ImageMetadataReader.readMetadata(path.toFile());
        ExifSubIFDDirectory exifMetadata = fileMetadata.getFirstDirectoryOfType(ExifSubIFDDirectory.class);
        XmpDirectory xmpMetadata = fileMetadata.getFirstDirectoryOfType(XmpDirectory.class);

        if (exifMetaDataIsPresent(exifMetadata)) {
            return exifMetadata.getDateOriginal(getDefault()).toInstant().atZone(systemDefault()).toLocalDate();
        } else if (xmpMetaDataIsPresent(xmpMetadata)) {
            return LocalDate.parse(xmpMetadata.getXmpProperties().get(XMP_CREATE_DATE_PROPERTY));
        }

        return null;
    }

    private boolean exifMetaDataIsPresent(ExifSubIFDDirectory exifMetadata) {
        return nonNull(exifMetadata) && nonNull(exifMetadata.getDateOriginal(getDefault()));
    }

    private boolean xmpMetaDataIsPresent(XmpDirectory xmpMetadata) {
        return nonNull(xmpMetadata) && nonNull(xmpMetadata.getXmpProperties());
    }
}
