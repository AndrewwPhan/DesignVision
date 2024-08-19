package com.cv.designvision.controllers.panels;

import com.cv.designvision.Main;
import com.cv.designvision.models.PanelContainer;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

public abstract class Panel {

    private final BorderPane parent;
    private final Node panel;

    public Panel(String viewPath) {
        parent = PanelContainer.getInstance().getPanelContainer();
        if (parent != null) panel = load(viewPath);
        else panel = null;
    }

    private Node load(String viewName) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource(viewName));
            return loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void show() {
        parent.setCenter(panel);
    }
}
