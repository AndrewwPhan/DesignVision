package com.cv.designvision.models;

import java.lang.reflect.Array;

public class ImageGrid {

    int[] activeCells = new int[9];

    public ImageGrid(int i, int i1) {

    }

    public void activateCell(int index) {
        activeCells[index] = 1;
    }

    public int[] getActiveCells() {
        return activeCells;
    }
}

