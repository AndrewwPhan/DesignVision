package com.cv.designvision.models;

import java.util.Arrays;
import java.util.stream.Collectors;

public class Review {
    private static final int DEFAULT_HEIGHT = 3;
    private static final int DEFAULT_WIDTH = 3;
    private final int width, height;
    private final int[] values;
    private String comment = "";

    public Review(){
        this(DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }

    public Review(int x, int y) {
        width = x;
        height = y;
        values = new int[width * height];
    }

    public void setTo(int idx, int value) {
        values[idx] = value;
    }

    public void setTo(int x, int y, int value) {
        setTo(getIdx(x, y), value);
    }

    public String getEncoded() {
        //return String.join('', reviewValues); // BS
        //return "".join(reviewValues); // BS!
        return Arrays.stream(values)
                .mapToObj(String::valueOf)
                .collect(Collectors.joining("")); // ARE YOU FN SERIOUS?
        // return result.toString();
    }

    public void setEncoded(String encodedReview){
        StringBuilder str = new StringBuilder(encodedReview);
        for(int i = 0; i < width * height; i++){
            values[i] = Integer.parseInt(String.valueOf(str.charAt(i)));
        }
    }

    public int getValue(int idx) {
        return values[idx];
    }

    public int getValue(int x, int y) {
        return values[getIdx(x, y)];
    }

    private int getIdx(int x, int y) {
        return width * y + x;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString(){
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                str.append(values[getIdx(j, i)]).append(" ");
            }
            str.append("\n");
        }
        return str.toString();
    }

    public int getImageNumberFromCell(int cellModifier) {
        // TODO: used only in test
        int imageNumber = 1;
        if (cellModifier > 1) {
            for (int i = cellModifier; i >= 1; i = i - 10) {
                imageNumber++;
            }
        }
        return imageNumber;
    }

    //For test class:
    public int getImageNumberFromID(String imageGridID) {
        // TODO: used only in test
        // Would not work with double-digit images. Only 6 images are planned,
        // Regex required for 10+ images
        return Integer.parseInt(imageGridID.substring(6,7));
    }
}