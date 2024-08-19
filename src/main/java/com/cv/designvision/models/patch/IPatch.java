package com.cv.designvision.models.patch;

import javafx.scene.paint.Color;

/**
 * This interface defines a Patch object, for representing colour patches.
 * Provides methods to access and manipulate the patch's colours.
 */
public interface IPatch {

    /**
     * Returns an array of {@link Color} objects representing the colours of the patch.
     *
     * @return An array of {@link Color} objects.
     */
    Color[] getColours();

    /**
     * Returns a String representation of the patch encoded in csv line format.
     *
     * @return The encoded String representation of the patch.
     */
    String getEncoded();


    /**
     * Sets the patch's colours based on a provided csv encoded String representation.
     *
     * @param code The csv encoded String representation of the patch colors.
     */
    void setColoursFromCode(String code);
}
