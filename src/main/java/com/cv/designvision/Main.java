package com.cv.designvision;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * Main application class for GUI setup and launch
 */
public class Main extends Application {

    public static final String TITLE = "DesignVision";
    public static final String DV_LOGO = "images/DVLogo.png";
    public static final String MAIN_VIEW = "views/main-view.fxml";

    Parent mainLayout;

    public static void main(String[] args) {
        launch();
    }

    /**
     * Main window setup for common components
     *
     * @param stage passed by JavaFX on launch
     */
    @Override
    public void start(Stage stage) throws IOException {
        mainLayout = loadMainLayout();
        prepareStage(stage);
        stage.show();
    }

    private Parent loadMainLayout() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(Main.class.getResource(MAIN_VIEW));
        return fxmlLoader.load();
    }

    private void prepareStage(Stage stage) {
        stage.setTitle(TITLE);
        stage.getIcons().add(getLogoImage());
        stage.setScene(new Scene(mainLayout));
    }

    private static Image getLogoImage() {
        try {
            InputStream logo = Main.class.getResourceAsStream(DV_LOGO);
            if (logo == null) throw new FileNotFoundException(
                    String.format("Cannot find %s.", DV_LOGO));
            return new Image(logo);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}