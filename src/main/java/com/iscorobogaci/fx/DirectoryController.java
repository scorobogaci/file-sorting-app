package com.iscorobogaci.fx;

import com.iscorobogaci.FileExceptionHandler;
import com.iscorobogaci.FileSorter;
import com.iscorobogaci.enums.SortingBy;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

//todo
//finish progressbar UI
//refactor thread to use streams
//add icon
//validate exception logging
//adjust tests

public class DirectoryController {

    @FXML
    private AnchorPane anchorId;

    @FXML
    private TextField textAreaSourceFolder;

    @FXML
    private TextField textAreaDestinationFolder;

    @FXML
    private ToggleGroup radioGroup;

    @FXML
    private ProgressBar progressBar;

    @FXML
    private Button startButton;

    private DirectoryChooser directoryChooser;
    private File sourceFolder;
    private File destinationFolder;
    private SortingBy sortingBy = SortingBy.WEEK;

    private CopyFileTask copyFileTask;

    public DirectoryController() {
        directoryChooser = new DirectoryChooser();
    }

    @FXML
    private void handleSourceFolderButton(ActionEvent actionEvent) {
        Stage stage = (Stage) anchorId.getScene().getWindow();
        File file = directoryChooser.showDialog(stage);
        textAreaSourceFolder.setEditable(false);
        if (file != null) {
            this.sourceFolder = file;
            textAreaSourceFolder.setText(file.getAbsolutePath());
        }
    }

    @FXML
    private void handleDestinationFolderButton(ActionEvent actionEvent) {
        Stage stage = (Stage) anchorId.getScene().getWindow();
        File file = directoryChooser.showDialog(stage);
        textAreaDestinationFolder.setEditable(false);
        if (file != null) {
            this.destinationFolder = file;
            textAreaDestinationFolder.setText(file.getAbsolutePath());
        }
    }

    @FXML
    private void handleStartButton(ActionEvent actionEvent) throws IOException {
        if (isSourceFolderNotSelected() || isDestinationFolderNotSelected()) {
            raiseAlert();
        } else {
            performSortingOnBackgroundThread();
        }
    }

    @FXML
    public void handleToggleButton(ActionEvent actionEvent) {
        RadioButton selectedToggle = (RadioButton) radioGroup.getSelectedToggle();
        this.sortingBy = SortingBy.valueOf(selectedToggle.getText());
    }

    private void openDestinationDirectoryInExplorer() {
        try {
            Desktop.getDesktop().open(destinationFolder);
        } catch (IOException e) {
            FileExceptionHandler.getInstance().logException(e);
        }
    }

    private void performSortingOnBackgroundThread() throws IOException {

        FileSorter fileSorter = new FileSorter(sourceFolder.toPath(), destinationFolder.toPath(), sortingBy);

        List<Path> inputFiles = Files.list(sourceFolder.toPath())
                .filter(path -> Files.isRegularFile(path))
                .collect(Collectors.toList());

        copyFileTask = new CopyFileTask(inputFiles, fileSorter);

        copyFileTask.setOnRunning((successEvent) -> {
            startButton.setVisible(false);
            progressBar.setVisible(true);
            progressBar.progressProperty().bind(copyFileTask.progressProperty());
        });

        copyFileTask.setOnSucceeded((successEvent) -> {
            startButton.setVisible(true);
            progressBar.setVisible(false);
            clearTextAreaFields();
            openDestinationDirectoryInExplorer();
            progressBar.progressProperty().unbind();
            progressBar.setProgress(0);
        });

        ExecutorService executorService = Executors.newFixedThreadPool(1);
        executorService.execute(copyFileTask);
        executorService.shutdown();
    }

    private boolean isSourceFolderNotSelected() {
        return textAreaSourceFolder.getText().isEmpty();
    }

    private boolean isDestinationFolderNotSelected() {
        return textAreaDestinationFolder.getText().isEmpty();
    }

    private void raiseAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        Stage alertStage = (Stage) alert.getDialogPane().getScene().getWindow();
        alertStage.getIcons().add(FxApplication.setApplicationIcon());
        alert.setTitle("Information");
        alert.setHeaderText("We need your input to process the files :)");
        alert.setContentText("Please select source folder and output folder.");
        alert.showAndWait();
    }

    private void clearTextAreaFields() {
        textAreaSourceFolder.clear();
        textAreaDestinationFolder.clear();
    }
}
