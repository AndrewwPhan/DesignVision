package com.cv.designvision.controllers.panels;

import com.cv.designvision.models.CurrentUser;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

public class PatchSetsPanel extends Panel {

    private static final String VIEW_PATH = "views/patchSets-view.fxml";

    public PatchSetsPanel() {
        super(VIEW_PATH);
    }

    @Override
    public void show(){
        CurrentUser.getInstance().notifyObservers();
        super.show();
    }
}
