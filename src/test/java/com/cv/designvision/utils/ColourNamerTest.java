package com.cv.designvision.utils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

class ColourNamerTest {

    public static final int ALLOY_ORANGE = 0xc46210;
    public static final int ALLOY_ORANGE_PLUS_ONE = 0xc46211;
    public static final int ALLOY_ORANGE_MINUS_ONE = 0xc4620F;
    public static final int ALABAMA_CRIMSON = 0xa32638;
    public static final int BETWEEN_ALABAMA_AND_ALLOY =
            (ALABAMA_CRIMSON+ ALLOY_ORANGE)/2;

    public static final String TEST_CSV_PATH =
            "src/test/java/com/cv/designvision/utils/data/testColourNames.csv";

    ColourNamer fullColourNames;
    @BeforeEach
    public void setup() throws IOException {
        IColourMap colourMap = new ColourMap(TEST_CSV_PATH);
        fullColourNames = new ColourNamer(colourMap);
    }

    @Test
    void colourMapUsingInterface_loadedWtihColoursFromMockCsvString() {
        //TODO: This is the only one using the mock now...
        // IO access is under test by ColourMapTest and uses a test data version of
        // the file... OK for tests to now just use this?
        // More bugs were missed using the mocked version, making it very not worth it.
        IColourMap fakedCsvColours = new MockColourMap();
        assertThat(fakedCsvColours.getColours()).hasSize(10);
    }

    @Test
    public void exactColourNumber_isColourName(){
        String expected = "Alloy Orange";
        String actual = fullColourNames.getName(ALLOY_ORANGE);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void red_returnsRed(){
        // This one caught the massive algo bit shift error.
        String expected = "Candy Apple Red";
        String actual = fullColourNames.getName(0xFF0000);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void red_hasRedValue(){
        fullColourNames.getName(0xFF0000);
        String actual = String.format("0x%06X", fullColourNames.getValue());
        assertThat(actual).isEqualTo("0xFF0800");
    }

    @Test
    public void colourNumberPlusOne_isThatColour(){
        String expected = "Alloy Orange";
        String actual = fullColourNames.getName(ALLOY_ORANGE_PLUS_ONE);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void colourNumberMinusOne_isThatColour(){
        String expected = "Alloy Orange";
        String actual = fullColourNames.getName(ALLOY_ORANGE_MINUS_ONE);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void colourNumberBetweenColours_isTheClosestOneOfThem(){
        // TODO: why is this one flaky?  Was it before algo fix?
        //String expected = "Amber";
        String expected = "Android Green";
        String actual = fullColourNames.getName(BETWEEN_ALABAMA_AND_ALLOY);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void closestColourValueCanBeAccessedAfterGettingAName(){
        String name = fullColourNames.getName(ALLOY_ORANGE_PLUS_ONE);
        int value = fullColourNames.getValue();
        assertThat(name).isEqualTo("Alloy Orange");
        assertThat(value).isEqualTo(ALLOY_ORANGE);
    }
}