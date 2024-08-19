package com.cv.designvision.utils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;

import static org.assertj.core.api.Assertions.assertThat;

class ColourMapTest {
    public static final String TEST_CSV_PATH =
            "src/test/java/com/cv/designvision/utils/data/testColourNames.csv";
    IColourMap colourMap;

    @BeforeEach void setup() throws IOException {
        colourMap = new ColourMap(TEST_CSV_PATH);
    }

    @Test
    void colourMap_loadedWithColoursAllTheColours() {
        assertThat(colourMap.getColours())
                .hasSize(765);
    }

    @Test
    void hasValueFromFirstEntryInFile() {
        // TODO: what to do about multiple entries?
        //  Currently, they are overwritten in the map to the last.
        String firstOne = "Air Force Blue (Raf)";
        String secondOne = "Rackley";
        assertThat(colourMap.getColours().get(0x5d8aa8))
                .isEqualTo(secondOne);
    }

    @Test
    void hasValueFromVeryEndOfFile() {
        String expected = "Zinnwaldite Brown";
        assertThat(colourMap.getColours().get(0x2c1608))
                .isEqualTo(expected);
    }

    @Test
    void handlesShortenedValueEntries() {
        String expected = "Amethyst";
        assertThat(colourMap.getColours().get(0x96c))
                .isEqualTo(expected);
    }

    @Test
    void knowsWhatEngineeringColourIs() {
        String expected = "International Orange (Engineering)";
        assertThat(colourMap.getColours().get(0xba160c))
                .isEqualTo(expected);
    }
}