package com.cv.designvision.models.patch;

import javafx.beans.binding.Bindings;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * This class represents a visual representation of a {@link Patch} object. It extends
 * {@link javafx.scene.layout.GridPane} and displays the patch's colors as a grid of
 * colored Rectangles.
 */
public class ViewPatch extends GridPane {

    protected static final int ROWS = 2;
    protected static final int COLS = 2;
    private final IPatch patch;

    protected final Rectangle[] rectangles;

    /**
     * Constructs a new ViewPatch instance based on a provided {@link IPatch} object
     * and sets the size of the visual representation.
     *
     * @param patch The Patch object to use as the data source for colours.
     * @param size The desired size (width and height) of the ViewPatch.
     */
    public ViewPatch(IPatch patch, int size) {
        this.patch = patch;
        rectangles = new Rectangle[ROWS * COLS];
        fillGridWithRectangles(size / COLS, size / ROWS);
    }

    /**
     * Add rectangles based on patch colours to GridPane (this)
     *
     * @param width  Width of quadrant
     * @param height Height of quadrant
     */
    private void fillGridWithRectangles(int width, int height) {
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                int idx = getIdx(row, col);
                rectangles[idx] = new Rectangle(width, height, patch.getColours()[idx]);
                rectangles[idx].setId(Integer.toString(idx));
                add(rectangles[idx], col, row);
            }
        }
    }

    /**
     * Sets the colour of a specific quadrant (rectangle) in the ViewPatch.
     * Also updates the corresponding color in the underlying Patch object.
     *
     * @param idx The index of the quadrant (rectangle) to set the colour for.
     * @param colour The new Color value to set for the quadrant.
     */
    protected void setColour(int idx, Color colour) {
        patch.getColours()[idx] = colour;
        rectangles[idx].setFill(colour);
    }

    /**
     * Utility method to convert row and column coordinates to index.
     *
     * @param row The row index to convert.
     * @param col The column index to convert.
     * @return The calculated index representing the position in the rectangles array.
     */
    private static int getIdx(int row, int col) {
        return ROWS * row + col;
    }
}
