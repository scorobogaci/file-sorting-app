import com.iscorobogaci.FileSorter;
import com.iscorobogaci.enums.DestinationFolder;
import com.iscorobogaci.enums.SortingBy;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static java.nio.file.Files.copy;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

//todo
//AVI creation data cannot be read by now
//FLV creation data cannot be read by now
//WMV creation data cannot be read by now
//MOV video gives NPE when processing it
public class FileSorterTest {

    @Rule
    public TemporaryFolder sourceFolder = new TemporaryFolder();

    @Rule
    public TemporaryFolder destinationFolder = new TemporaryFolder();

    private Path sourcePath;

    private Path destinationPath;

    @Test
    public void testCopyByExifData() throws Exception {
        //picture has creation date EXIF : 2018-10-07
        loadData("pictures/photo_exif.jpg");
        new FileSorter(sourcePath, destinationPath, SortingBy.WEEK).copy();

        assertNumberOfCreatedFolders(3);

        assertFileNameAndLocation("photo_exif.jpg");

        assertDestinationPath(DestinationFolder.PICTURES, "2018-10-07");
    }

    @Test
    public void testCopyMP4VideoMetaData() throws Exception {
        //video file has creation date : 2018-10-28
        loadData("video/mp4_video.mp4");
        new FileSorter(sourcePath, destinationPath, SortingBy.WEEK).copy();

        assertNumberOfCreatedFolders(3);

        assertFileNameAndLocation("mp4_video.mp4");

        assertDestinationPath(DestinationFolder.VIDEO, "2018-10-28");

    }

    @Test
    public void testUnsupportedFileTypeCopy() throws Exception {
        loadData("pictures/home.png");

        new FileSorter(sourcePath, destinationPath, SortingBy.WEEK).copy();

        assertNumberOfCreatedFolders(2);

        assertFileNameAndLocation("home.png");

        assertDestinationPath(DestinationFolder.NOT_SORTED, null);
    }

    @Test
    public void testSortByMonth() throws Exception {
        loadData("pictures/photo_exif.jpg");
        new FileSorter(sourcePath, destinationPath, SortingBy.MONTH).copy();

        assertNumberOfCreatedFolders(3);

        assertFileNameAndLocation("photo_exif.jpg");

        assertDestinationPath(DestinationFolder.PICTURES, "2018-10-01");
    }

    private void assertFileNameAndLocation(String fileName) throws IOException {
        List<Path> files = Files.walk(destinationPath)
                .filter(Files::isRegularFile)
                .collect(Collectors.toList());

        assertEquals(1, files.size());
        assertEquals(fileName, files.get(0).getFileName().toString());
    }

    private void assertNumberOfCreatedFolders(int expectedNumber) throws IOException {
        List<Path> folders = Files.walk(destinationPath)
                .filter(Files::isDirectory)
                .collect(Collectors.toList());
        assertEquals(expectedNumber, folders.size());
    }

    private void assertDestinationPath(DestinationFolder destinationFolder, String folderName) {
        File file = new File(destinationPath.toString(), destinationFolder.value());
        assertTrue(file.exists());
        if (Objects.nonNull(folderName)) {
            File file1 = new File(destinationPath.toString().concat(File.separator).concat(destinationFolder.value()), folderName);
            assertTrue(file1.exists());
        }
    }

    private void loadData(String fileName) throws IOException {
        this.sourcePath = sourceFolder.getRoot().toPath();
        this.destinationPath = destinationFolder.getRoot().toPath();
        ClassLoader classLoader = getClass().getClassLoader();
        File fileWithEXIFData = new File(classLoader.getResource(fileName).getFile());
        copy(fileWithEXIFData.toPath(), sourcePath.resolve(fileWithEXIFData.toPath().getFileName()), REPLACE_EXISTING);
    }

}