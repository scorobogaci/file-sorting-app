import java.util.Scanner;
import java.util.logging.Logger;

public class MainApplication {

    private static final Logger LOGGER = Logger.getLogger(MainApplication.class.getName());


    public static void main(String[] args) {

        LOGGER.info("******************** Catalogue Generator ********************");
        Scanner inputReader = new Scanner(System.in);
        System.out.println("Enter the source directory. Format : [D://pictures//...] :");
        String sourceDirectory = inputReader.nextLine();
        System.out.println("Enter the destination directory for catalogue. Format : [D://catalogue//...] :");
        String destinationDirectory = inputReader.nextLine();
        LOGGER.info("Generation in progress ...");

        FileCatalogue fileCatalogue = new FileCatalogue(sourceDirectory, destinationDirectory);
        fileCatalogue.generateCatalogue();
        LOGGER.info("Catalogue is generated! Go to : " + destinationDirectory + " to check it out!");

    }
}
