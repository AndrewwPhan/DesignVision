package com.cv.designvision.controllers.panels;

import com.cv.designvision.models.CurrentUser;

public class TemplatesPanel extends Panel {
    private static final String VIEW_PATH = "views/templates-view.fxml";

    public TemplatesPanel() {
        super(VIEW_PATH);
    }

    @Override
    public void show(){
        CurrentUser.getInstance().notifyObservers();
        super.show();
    }
}
