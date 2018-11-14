package com.iscorobogaci.fx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class FxApplication extends Application {

    private static final String FXML_PATH = "/fxml/sample.fxml";
    private static final String APPLICATION_ICON_PATH = "/icons/application-icon.png";
    private static final String APPLICATION_TITLE = "FSORT";

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource(FXML_PATH));
        primaryStage.setTitle(APPLICATION_TITLE);
        primaryStage.setScene(new Scene(root));
        primaryStage.setResizable(false);
        primaryStage.getIcons().add(setApplicationIcon());
        primaryStage.show();
    }

    public static Image setApplicationIcon() {
        return new Image(FxApplication.class.getResource(APPLICATION_ICON_PATH).toExternalForm());
    }

    public static void main(String[] args) {
        launch(args);
    }
}
