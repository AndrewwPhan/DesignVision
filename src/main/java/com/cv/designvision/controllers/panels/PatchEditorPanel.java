package com.cv.designvision.controllers.panels;

import com.cv.designvision.models.CurrentUser;

public class PatchEditorPanel extends Panel {

    private static final String VIEW_PATH = "views/patchEditor-view.fxml";

    public PatchEditorPanel() {
        super(VIEW_PATH);
    }
    @Override
    public void show(){
        CurrentUser.getInstance().notifyObservers();
        super.show();
    }
}
