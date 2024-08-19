package com.cv.designvision.controllers;

import com.cv.designvision.Services.IObserver;
import com.cv.designvision.models.CurrentUser;
import com.cv.designvision.models.IUser;
import com.cv.designvision.models.patch.IPatch;
import com.cv.designvision.models.patch.ViewPatch;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Shape;

import java.io.IOException;

public class TemplatesController implements IObserver {

    IUser user;
    @FXML
    TabPane templatesTabPane;
    @FXML
    Pane patchPane;

    @FXML
    public void initialize() throws IOException {
        CurrentUser.getInstance().register(this);
    }

    @FXML
    private void onNext() {
        IPatch patch = user.patches().next();
        updateDisplay(patch);
    }

    @FXML
    private void onPrevious() {
        IPatch patch = user.patches().prev();
        updateDisplay(patch);
    }

    private void updateDisplay(IPatch active) {
        setActivePatchView(active);
        applyPatchToAll(active);
    }

    private void setActivePatchView(IPatch patch) {
        ObservableList<Node> nodes = patchPane.getChildren();
        nodes.clear();
        nodes.add(new ViewPatch(patch, (int) patchPane.getWidth()));
    }

    private void applyPatchToAll(IPatch patch) {
        for (Tab tab : templatesTabPane.getTabs()) {
            AnchorPane templatePane = getTemplatePane(tab);
            applyPatchToTemplate(templatePane, patch);
        }
    }

    private static AnchorPane getTemplatePane(Tab tab) {
        AnchorPane tabBody = (AnchorPane) tab.getContent();
        if (tabBody.getChildren().isEmpty())
            throw new RuntimeException("No body AnchorPane found in tab");
        return (AnchorPane) tabBody.getChildren().get(0);
    }

    private static void applyPatchToTemplate(AnchorPane templatePane, IPatch patch) {
        for (Node node : templatePane.getChildren()) {
            if (node instanceof Shape shape) {
                String id = shape.getId() != null ? shape.getId() : "";
                int cell = parseId(id);
                if (cell > 0) shape.setFill(patch.getColours()[cell - 1]);
            }
        }
    }

    private static int parseId(String id) {
        try {
            return Integer.parseInt(id);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    @Override
    public <T> void update(T value) {
        user = (IUser) value;
        updateDisplay(user.patches().getActive());
    }
}
