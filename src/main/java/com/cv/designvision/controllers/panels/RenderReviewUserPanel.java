package com.cv.designvision.controllers.panels;

import com.cv.designvision.controllers.IRenderReviewPanel;

public class RenderReviewUserPanel extends Panel implements IRenderReviewPanel {

    private static final String VIEW_PATH_USER = "views/render-review-user-view.fxml";

    public RenderReviewUserPanel() {
        super(VIEW_PATH_USER);
    }
}
