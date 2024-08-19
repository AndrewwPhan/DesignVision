package com.cv.designvision.models;

import com.cv.designvision.controllers.IRenderReviewPanel;
import javafx.scene.layout.BorderPane;

public class PanelContainer {
    private static PanelContainer instance = null;
    private BorderPane panelContainer;
    private IRenderReviewPanel reviewPanel;

    private PanelContainer() {

    }

    public static PanelContainer getInstance() {
        if (instance == null) instance = new PanelContainer();
        return instance;
    }

    public void setPanelContainer(BorderPane panelContainer){
        this.panelContainer = panelContainer;
    }

    public BorderPane getPanelContainer() {
        return panelContainer;
    }

    public void setReviewPanel(IRenderReviewPanel reviewPanel) {
        this.reviewPanel = reviewPanel;
    }

    public IRenderReviewPanel getReviewPanel() {
        return reviewPanel;
    }
}
