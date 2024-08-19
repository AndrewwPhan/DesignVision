package com.cv.designvision.models.patch;

import javafx.event.ActionEvent;
import javafx.scene.control.ColorPicker;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;

/**
 * This class extends {@link ViewPatch} and provides an editable visual representation
 * of a {@link Patch} object. It allows users to click on individual quadrants (rectangles)
 * to activate a colour picker and modify the corresponding colour in the patch.
 */
public class EditableViewPatch extends ViewPatch {
    private final ColorPicker[] pickers = new ColorPicker[ROWS * COLS];

    /**
     * Constructs a new square EditableViewPatch instance based on provided
     * {@link IPatch} object and sets the size of the visual representation.
     *
     * @param patch The Patch object to use as source.
     * @param size The desired size (width and height) of the EditableViewPatch.
     */
    public EditableViewPatch(IPatch patch, int size) {
        super(patch, size);
        setOnMouseClicked(this::handleClick);
        addPickers();
    }

    /**
     * Adds a {@link ColorPicker} to each quadrant (rectangle) of the ViewPatch.
     * These ColorPickers are hidden.
     */
    private void addPickers() {
        for (int i = 0; i < ROWS * COLS; i++) {
            pickers[i] = new ColorPicker();
            pickers[i].setOnAction(this::onColourPick);
            pickers[i].setId(Integer.toString(i));
            pickers[i].setVisible(false);
            add(pickers[i], i % COLS, i / COLS);
        }
    }

    /**
     * Handles mouse clicks on the ViewPatch.
     * Associated patch quadrant colour picker is shown.
     *
     * @param event The MouseEvent object representing the click.
     */
    private void handleClick(MouseEvent event) {
        if (event.getTarget().getClass() != Rectangle.class)
            return;
        Rectangle rect = (Rectangle) event.getTarget();
        pickers[Integer.parseInt(rect.getId())].show();
    }

    /**
     * Handles the user's selection colour picker.
     * Sets the colour of the matching quadrant (rectangle) in the patch.
     *
     * @param actionEvent Triggered by the user picking a colour.
     */
    private void onColourPick(ActionEvent actionEvent) {
        ColorPicker picker = (ColorPicker) actionEvent.getSource();
        int idx = Integer.parseInt(picker.getId());
        setColour(idx, picker.getValue());
    }
}
