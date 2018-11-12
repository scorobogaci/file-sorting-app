package com.iscorobogaci.fx;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;

public class DirectoryController {

    @FXML
    private AnchorPane anchorId;

    @FXML
    private TextField sourceFolderTextAreaId;

    @FXML
    private TextField textFieldDestinationFolderId;

    private DirectoryChooser directoryChooser;

    private File sourceFolder;

    private File destinationFolder;

    public DirectoryController() {
        directoryChooser = new DirectoryChooser();
    }

    @FXML
    private void handleSourceFolderButton(ActionEvent actionEvent) {
        Stage stage = (Stage) anchorId.getScene().getWindow();
        File file = directoryChooser.showDialog(stage);
        sourceFolderTextAreaId.setEditable(false);
        if (file != null) {
            this.sourceFolder = file;
            sourceFolderTextAreaId.setText(file.getAbsolutePath());
        }
    }

    @FXML
    private void handleDestinationFolderButton(ActionEvent actionEvent) {
        Stage stage = (Stage) anchorId.getScene().getWindow();
        File file = directoryChooser.showDialog(stage);
        textFieldDestinationFolderId.setEditable(false);
        if (file != null) {
            this.destinationFolder = file;
            textFieldDestinationFolderId.setText(file.getAbsolutePath());
        }
    }

    @FXML
    private void handleStartButton(ActionEvent actionEvent){
        System.out.println(sourceFolder.toPath());
        System.out.println(destinationFolder.toPath());
    }
}
