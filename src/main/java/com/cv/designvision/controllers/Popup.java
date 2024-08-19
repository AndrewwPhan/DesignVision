package com.cv.designvision.controllers;

import javafx.scene.control.Alert;
import javafx.scene.control.*;
public class Popup {

    public static final Alert.AlertType INFO = Alert.AlertType.INFORMATION;
    public static final Alert.AlertType ERROR = Alert.AlertType.ERROR;



    public static void show(Alert.AlertType type, String title, String msg) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
