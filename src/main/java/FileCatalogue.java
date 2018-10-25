import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.TrueFileFilter;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.WeekFields;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FileCatalogue {

    private static final Logger LOGGER = Logger.getLogger(FileCatalogue.class.getName());

    private String sourceRootFolder;
    private String destinationRootFolder;

    public FileCatalogue(String sourceRootFolder, String destinationRootFolder) {
        this.sourceRootFolder = sourceRootFolder;
        this.destinationRootFolder = destinationRootFolder;
    }

    public void generateCatalogue() {
        final List<File> sourceFiles = readSourceFiles(this.sourceRootFolder);
        sourceFiles.forEach(file -> {
            try {
                putFileInCatalogue(file);
            } catch (IOException e) {
                LOGGER.log(Level.SEVERE, e.toString(), e);
            }
        });
    }

    private void putFileInCatalogue(File file) throws IOException {
        final LocalDate fileCreationDate = readFileCreationTimeAttribute(file);
        String destinationFolder = generateFolderName(fileCreationDate);
        copyFileToCatalogue(file, destinationFolder);
    }

    private void copyFileToCatalogue(File file, String folderName) throws IOException {
        final Path filePath = Paths.get(this.destinationRootFolder + File.separator + folderName + File.separator + file.getName());
        if (!Files.exists(filePath)) {
            Files.createDirectories(filePath.getParent());
            Files.copy(file.toPath(), filePath, StandardCopyOption.REPLACE_EXISTING);
        }
    }

    private String generateFolderName(LocalDate fileCreationDate) {
        final DayOfWeek firstDayOfWeek = WeekFields.of(Locale.getDefault()).getFirstDayOfWeek();
        return fileCreationDate.with(TemporalAdjusters.previousOrSame(firstDayOfWeek)).toString();
    }

    private LocalDate readFileCreationTimeAttribute(File file) throws IOException {
        final Path path = Paths.get(file.getPath());
        final BasicFileAttributes fileAttributes = Files.readAttributes(path, BasicFileAttributes.class);
        final FileTime fileCreationTime = fileAttributes.creationTime();
        return fileCreationTime.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    private List<File> readSourceFiles(String sourceRootFolder) {
        final File rootSourceDirectory = new File(sourceRootFolder);
        return (List<File>) FileUtils.listFiles(rootSourceDirectory, TrueFileFilter.INSTANCE, TrueFileFilter.INSTANCE);
    }

}
