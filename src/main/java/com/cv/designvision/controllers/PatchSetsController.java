package com.cv.designvision.controllers;

import com.cv.designvision.Services.IObserver;
import com.cv.designvision.models.CurrentUser;
import com.cv.designvision.models.IUser;
import com.cv.designvision.Services.UserDataList;
import com.cv.designvision.Services.IDataFile;
import com.cv.designvision.Services.DataCSV;

import com.cv.designvision.models.patch.IPatch;
import com.cv.designvision.models.patch.SelectableViewPatch;
import com.cv.designvision.models.patch.PatchSetManager;

import javafx.fxml.FXML;
import javafx.stage.Stage;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;

public class PatchSetsController implements IObserver {
    private static final int PATCH_SIZE = 100;

    @FXML
    private GridPane patchSetPane;

    IUser user;
    SelectableViewPatch highlighted;

    @FXML
    private void initialize() {
        CurrentUser.getInstance().register(this);
        patchSetPane.setOnMouseClicked(this::handlePatchClick);
    }

    private void handlePatchClick(MouseEvent event) {
        if (event.getTarget().getClass() != Rectangle.class)
            return;
        Rectangle clicked = (Rectangle) event.getTarget();
        SelectableViewPatch selectablePatch = (SelectableViewPatch) clicked.getParent();
        highLight(selectablePatch);
        user.patches().setActiveIndex(selectablePatch.getPatchIdx());
    }

    @FXML
    private void onImport() {
        String filePath = requestFilePath();
        if (filePath.isEmpty())
            return;

        IDataFile file = new DataCSV(filePath);
        PatchSetManager setManager = new PatchSetManager(file);
        UserDataList<IPatch> patches;
        try { patches = setManager.importFromCSV(); }
        catch (IOException e) {
            popupMsg("Import", "Could read file", e);
            return;
        }

        if (patches == null || patches.size() == 0) {
            popupMsg("Import", "Empty Patch-Set file", null);
            return;
        }

        user.patches().clear();
        for (IPatch patch : patches) user.patches().add(patch);
        user.patches().setActiveIndex(0);
        loadPatches();
    }

    @FXML
    private void onExport() {
        FileChooser fileChooser = getFileChooser("Patch-Set Export");
        File selectedFile = fileChooser.showSaveDialog(getStage());
        if (selectedFile == null)
            return;

        String filePath = selectedFile.getAbsolutePath();
        IDataFile file = new DataCSV(filePath);
        PatchSetManager setManager = new PatchSetManager(file);
        try {
            setManager.exportToCSV(user.patches());
        } catch (IOException e) {
            popupMsg("Export", "Could not save file", e);
        }
    }

    @FXML
    private void onDelete() {
        user.patches().remove();
        loadPatches();
    }

    private void highLight(SelectableViewPatch viewPatch) {
        if (highlighted != null)
            highlighted.setHighlight(false);
        viewPatch.setHighlight(true);
        highlighted = viewPatch;
    }

    private void loadPatches() {
        patchSetPane.getChildren().clear();
        int cols = patchSetPane.getColumnCount();
        int idx = 0;
        for (IPatch patch : user.patches()) {
            SelectableViewPatch viewPatch = new SelectableViewPatch(patch, PATCH_SIZE, idx);
            patchSetPane.add(viewPatch, idx % cols, idx / cols);
            idx++;
        }
        idx = user.patches().getActiveIndex();
        highLight((SelectableViewPatch) patchSetPane.getChildren().get(idx));
    }

    private static void popupMsg(String title, String reason, IOException e) {
        String msg = String.format("%s: %s\n",reason, e);
        Popup.show(Popup.ERROR, title, msg);
    }

    private String requestFilePath() {
        FileChooser fileChooser = getFileChooser("Patch-Set Import");
        File selectedFile = fileChooser.showOpenDialog(getStage());
        if (selectedFile == null) return "";
        return selectedFile.getAbsolutePath();
    }

    private static FileChooser getFileChooser(String title) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("CSV files (*.csv)", "*.csv"));
        fileChooser.setTitle(title);
        fileChooser.setInitialDirectory(new File("Example Patch-Sets"));
        return fileChooser;
    }

    private Stage getStage() {
        return (Stage) patchSetPane.getScene().getWindow();
    }

    @Override
    public <T> void update(T value) {
        user = (IUser) value;
        loadPatches();
    }
}