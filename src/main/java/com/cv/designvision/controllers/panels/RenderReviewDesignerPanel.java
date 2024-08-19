package com.cv.designvision.controllers.panels;

import com.cv.designvision.controllers.IRenderReviewPanel;

public class RenderReviewDesignerPanel extends Panel implements IRenderReviewPanel {

    private static final String VIEW_PATH_DESIGNER = "views/render-review-designer-view.fxml";

    public RenderReviewDesignerPanel() {
        super(VIEW_PATH_DESIGNER);
    }
}
