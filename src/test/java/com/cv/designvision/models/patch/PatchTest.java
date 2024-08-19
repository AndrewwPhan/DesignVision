package com.cv.designvision.models.patch;

import javafx.scene.paint.Color;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

class PatchTest {

    @Test
    void encodesColorsToCSV() {
        Patch patch = new Patch();
        String encoded = "0xff0000ff, 0xff0000ff, 0xff0000ff, 0xff0000ff";
        Arrays.fill(patch.getColours(), Color.RED);
        assertThat(patch.getEncoded()).isEqualTo(encoded);
    }

    @Test
    void decodesCsvStringToSetColours() {
        Patch patch = new Patch();
        Arrays.fill(patch.getColours(), Color.RED);
        String encoded = "0xff0000ff, 0xff0000ff, 0xff0000ff, 0xff0000ff";
        Color[] expected = new Color[]{
                Color.RED, Color.RED, Color.RED, Color.RED
        };
        patch.setColoursFromCode(encoded);
        Color[] actual = patch.getColours();
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void buggedNonParsingColours_canNowBeParsed() {
        String expected = "0xcaebf2ff, 0xa9a9a9ff, 0xefefefff, 0xff3b3fff";
        Patch patch = new Patch(expected);
        String actual = patch.getEncoded();
        assertThat(actual).isEqualTo(expected);
    }
}