import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class FileCatalogueTest {

    private File destinationDirectory;

    @Test
    public void testCatalogue() throws Exception {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("file1.jpg").getFile());
        FileCatalogue fileCatalogue = new FileCatalogue(file.getParent(), file.getParent() + File.separator + "catalogue");
        fileCatalogue.generateCatalogue();
        destinationDirectory = new File(file.getParent() + File.separator + "catalogue");
        assertGeneratedCatalogue(destinationDirectory);
    }

    private void assertGeneratedCatalogue(File destinationDirectory) {
        assertTrue(destinationDirectory.exists());
        assertTrue(destinationDirectory.isDirectory());
        assertEquals(1, destinationDirectory.list().length);
    }

    @After
    public void tearDown() throws IOException {
        FileUtils.deleteDirectory(destinationDirectory);
    }
}
