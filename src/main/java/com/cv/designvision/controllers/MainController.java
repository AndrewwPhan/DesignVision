package com.cv.designvision.controllers;

import com.cv.designvision.controllers.panels.*;
import com.cv.designvision.models.CurrentUser;
import com.cv.designvision.models.PanelContainer;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.util.Objects;

public class MainController {

    @FXML
    private BorderPane panelContainer;

    @FXML
    private Button menu1, menu2, menu3, menu4, menu5, menu6, menu7;

    private Button[] menuButtons;
    private Panel profilePanel;
    private Panel loginPanel;
    private Panel patchEditorPanel;
    private Panel patchSetsPanel;
    private TemplatesPanel templatesPanel;
    private Panel accessibilityPanel;
    private Button activeMenuButton;

    /**
     * Called by JavaFX when MainController binds to main-view
     *
     * @throws IOException When view resource is missing
     */
    @FXML
    public void initialize() throws IOException {

        PanelContainer.getInstance().setPanelContainer(panelContainer);

        menu1.setText("User Profile");
        profilePanel = new ProfilePanel();
        loginPanel = new LoginPanel();

        menu2.setText("Patch Editor");
        patchEditorPanel = new PatchEditorPanel();

        menu3.setText("Patch Sets");
        patchSetsPanel = new PatchSetsPanel();

        menu4.setText("Accessibility");
        accessibilityPanel = new AccessibilityPanel();

        menu5.setText("Templates");
        templatesPanel = new TemplatesPanel();

        menu6.setText("Render Review");
        PanelContainer.getInstance().setReviewPanel(new RenderReviewUserPanel());

        menu7.setText("Save All");

        menuButtons = new Button[]{menu1, menu2, menu3, menu4, menu5, menu6, menu7};

        // logged in user listener
        CurrentUser instance = CurrentUser.getInstance();
        instance.loggedInProperty().addListener((observable, oldValue, newValue) -> setActiveButtons(newValue));

        setActiveButtons(false);
        activeMenuButton = menu1;
        loginPanel.show();
    }

    private void setActiveButtons(Boolean state) {
        for (Button button : menuButtons) {
            button.setDisable(!state);
        }
    }

    @FXML
    private void onMenu(Event event) {
        Button button = (Button) event.getTarget();
        if (!Objects.equals(button.getId(), "menu7"))
            swapActiveMenuButton(button);

        switch (button.getId()) {
            case "menu1":
                profilePanel.show();
                break;
            case "menu2":
                patchEditorPanel.show();
                break;
            case "menu3":
                patchSetsPanel.show();
                break;
            case "menu4":
                accessibilityPanel.show();
                break;
            case "menu5":
                templatesPanel.show();
                break;
            case "menu6":
                PanelContainer.getInstance().getReviewPanel().show();
                break;
            case "menu7":
                CurrentUser.getInstance().saveToDb();
                break;
        }
    }

    private void swapActiveMenuButton(Button button) {
        activeMenuButton.getStyleClass().remove("menuButtonActive");
        activeMenuButton.getStyleClass().add("menuButton");

        button.getStyleClass().remove("menuButton");
        button.getStyleClass().add("menuButtonActive");

        activeMenuButton = button;
    }

}
