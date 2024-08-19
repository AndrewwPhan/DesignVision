package com.cv.designvision.models.patch;

import javafx.scene.paint.Color;
import java.util.Arrays;

/**
 * This class represents a Patch object for representing colour patches.
 * Provides functionalities to manage the patch's colors.
 */
public class Patch implements IPatch {
    private static final int NUM_COLOURS = 4;
    private final Color[] colours;

    /**
     * Constructs a new Patch object with default colors (Red, Green, Blue, Yellow).
     */
    public Patch() {
        colours = new Color[]{
                Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW
        };
    }
    /**
     * Constructs a new Patch object by parsing a provided encoded csv string.
     *
     * @param code The csv String representation of the patch colours.
     *             Comma-separated list of 8-character hexadecimal colour codes (including alpha channel).
     * @throws RuntimeException if the provided code string is null, empty, or
     * contains less colors than the expected number.
     */
    public Patch(String code) {
        colours = new Color[NUM_COLOURS];
        setColoursFromCode(code);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Color[] getColours() {
        return colours;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getEncoded() {
        StringBuilder sb = new StringBuilder();
        sb.append(toString().substring(1));
        return sb.substring(0, sb.length() - 1);
    }

    /**
     * {@inheritDoc}
     *
     * The expected format is a comma-separated list of 8-character hexadecimal color codes
     * (including alpha channel).
     *
     * @throws RuntimeException if the provided code string is null, empty, or
     * contains less colors than the expected number.
     */
    @Override
    public void setColoursFromCode(String code) {
        if (code == null || code.isEmpty()) return;
        String[] codes = code.split(",");
        if (codes.length < colours.length)
            throw new RuntimeException("Color code length error");
        for (int i = 0; i < codes.length; ++i) {
            String hex = codes[i].trim().substring(2);
            double r = Integer.parseInt(hex.substring(0, 2), 16) / 255.0;
            double g = Integer.parseInt(hex.substring(2, 4), 16) / 255.0;
            double b = Integer.parseInt(hex.substring(4, 6), 16) / 255.0;
            double a = Integer.parseInt(hex.substring(6, 8), 16) / 255.0;
            colours[i] = new Color(r, g, b, a);
        }
    }

    /**
     *  @return String array representation of Patch {@link Color} hashCodes
     */
    @Override
    public String toString(){
        return Arrays.toString(colours);
    }
}
