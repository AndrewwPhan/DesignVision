package com.cv.designvision.models;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Array;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ImageGridTest {

    @Test void activateCell0(){
        ImageGrid ig = new ImageGrid(200,300);
        ig.activateCell(0);
        int[] activeCells = ig.getActiveCells();
        assertEquals(1, activeCells[0]);

    }

    @Test void activateCell1(){
        ImageGrid ig = new ImageGrid(200,300);
        ig.activateCell(0);
        int[] activeCells = ig.getActiveCells();
        assertEquals(1, activeCells[0]);

    }

}