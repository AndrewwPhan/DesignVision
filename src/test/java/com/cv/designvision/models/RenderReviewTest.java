package com.cv.designvision.models;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

class RenderReviewTest {

    Review review = new Review();


    @Test
    void emptyReview_hasResultStringWithAllZeros(){
        // Arrange
        Review emptyReview = new Review();
        // Act
        String actual = emptyReview.getEncoded();
        String expected = "000000000";
        // Assert
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void getByIndex_getsIntFromIndex() {
        // FIXME: overload int getReview(idx) smells BAD
        review.setTo(0, 1);
        int actual = review.getValue(0);
        int expected = 1;
        assertThat(actual).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({
            "0,100000000",
            "1,010000000",
            "2,001000000",
            "3,000100000",
            "6,000000100",
            "8,000000001",
    })
    void getEncodedReviewString_matchesReviewValues(int idx, String expected) {
        review.setTo(idx,1);
        String actual = review.getEncoded();
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void setByIndex_canClearsValueAtIndex() {
        review.setTo(0,1);
        review.setTo(0,0);
        String actual = review.getEncoded();
        String expected = "000000000";
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void whenPrinted_formatAsAGrid() {
        String result = String.format("%s", review);
        String expected = "0 0 0 \n0 0 0 \n0 0 0 \n";
        assertThat(result).isEqualTo(expected);
    }
    @Test
    void printFormattedGridWithValue() {
        review.setTo(2,2,1);
        String result = String.format("%s", review);
        String expected = "0 0 0 \n0 0 0 \n0 0 1 \n";
        assertThat(result).isEqualTo(expected);
    }

    @Test
    void setsIntegerAtCoord() {
        review.setTo(2,2,1);
        int actual = review.getValue(2, 2);
        int expected = 1;
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void biggerGrid_hasMoreCells() {
        Review bigGrid = new Review(100, 100);
        int actual = bigGrid.getEncoded().length();
        int expected = 100 * 100;
        assertThat(actual).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({
            "Image_9_Test, 9",
            "Image_9, 9",
    })
    void imageNumber_isEqualToID(String imageID, int expected) {
        int actual = review.getImageNumberFromID(imageID);
        assertThat(actual).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({
            "1, 1",
            "10, 2",
            "19, 3",
            "28, 4",
            "37, 5",
            "46, 6",
    })
    void imageNumber_isEqualToCell(int cellModifier, int expected) {
        int actual = review.getImageNumberFromCell(cellModifier);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void canConvertIdxToRow() {

    }

}