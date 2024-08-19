package com.cv.designvision.controllers;

import com.cv.designvision.Services.IObserver;
import com.cv.designvision.models.CurrentUser;
import com.cv.designvision.models.IUser;
import com.cv.designvision.models.patch.EditableViewPatch;
import com.cv.designvision.models.patch.Patch;
import com.cv.designvision.models.patch.IPatch;
import javafx.fxml.FXML;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Pane;

public class PatchEditorController implements IObserver {

    private static final int PATCH_SIZE = 400;
    @FXML
    private Pane patchContainer;

    private IUser user;

    @FXML
    public void initialize() {
        CurrentUser.getInstance().register(this);
        patchContainer.setOnScroll(this::onScroll);
    }

    private void onScroll(ScrollEvent event){
        IPatch patch = event.getDeltaY() > 0 ?
                user.patches().next() : user.patches().prev();
        refreshContainer(patch);
    }

    private void refreshContainer(IPatch patch) {
        //int patchSize = (int)patchContainer.getWidth();
        // TODO: auto size isn't going to work unless we
        //  sort out all the other resizable stuff
        patchContainer.getChildren().clear();
        patchContainer.getChildren().add(new EditableViewPatch(patch, PATCH_SIZE));
    }

    @FXML
    private void onNew(){
        IPatch patch = new Patch();
        user.patches().add(patch);
        refreshContainer(patch);
    }

    @FXML
    private void onPrevious() {
        refreshContainer(user.patches().prev());
    }

    @FXML
    private void onNext() {
        refreshContainer(user.patches().next());
    }

    @FXML
    private void onDelete(){
        if (user.patches().size() == 1)
            return; // TODO: fail msg
        user.patches().remove();
        refreshContainer(user.patches().getActive());
    }

    @Override
    public <T> void update(T value) {
        user = (IUser) value;
        refreshContainer(user.patches().getActive());
    }
}