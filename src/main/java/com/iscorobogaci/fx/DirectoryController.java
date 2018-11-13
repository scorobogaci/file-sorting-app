package com.iscorobogaci.fx;

import com.iscorobogaci.FileExceptionHandler;
import com.iscorobogaci.FileSorter;
import com.iscorobogaci.enums.SortingBy;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class DirectoryController {

    @FXML
    private AnchorPane anchorId;

    @FXML
    private TextField textAreaSourceFolder;

    @FXML
    private TextField textAreaDestinationFolder;

    @FXML
    private ToggleGroup radioGroup;

    private DirectoryChooser directoryChooser;
    private File sourceFolder;
    private File destinationFolder;
    private SortingBy sortingBy;


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
    private void handleStartButton(ActionEvent actionEvent) {
        if (isSourceFolderNotSelected() || isDestinationFolderNotSelected()) {
            raiseAlert();
        } else {
            FileSorter fileSorter = new FileSorter(sourceFolder.toPath(), destinationFolder.toPath(), sortingBy);
            try {
                fileSorter.copy();
                openDestinationDirectoryInExplorer();
            } catch (IOException e) {
                FileExceptionHandler.getInstance().logException(e);
            }
            clearTextAreaFields();
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

    private boolean isSourceFolderNotSelected() {
        return textAreaSourceFolder.getText().isEmpty();
    }

    private boolean isDestinationFolderNotSelected() {
        return textAreaDestinationFolder.getText().isEmpty();
    }

    private void raiseAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
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
