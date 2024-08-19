package com.cv.designvision.utils;

import java.util.*;

public class MockColourMap implements IColourMap {

    public static final int NAME_COLUMN = 0;
    public static final int VALUE_COLUMN = 4;
    private final String CSV =
            "Air Force Blue (Raf),,,air_force_blue_raf,#5d8aa8,,93,138,168\n" +
            "Air Force Blue (Usaf),,,air_force_blue_usaf,#00308f,,0,48,143\n" +
            "Air Superiority Blue,,,air_superiority_blue,#72a0c1,,114,160,193\n" +
            "Alabama Crimson,,,alabama_crimson,#a32638,,163,38,56\n" +
            "Alice Blue,,,alice_blue,#f0f8ff,,240,248,255\n" +
            "Alizarin Crimson,,,alizarin_crimson,#e32636,,227,38,54\n" +
            "Alloy Orange,,,alloy_orange,#c46210,,196,98,16\n" +
            "Almond,,,almond,#efdecd,,239,222,205\n" +
            "Amaranth,,,amaranth,#e52b50,,229,43,80\n" +
            "Amber,,,amber,#ffbf00,,255,191,0";

    private final Map<Integer, String> colours = new HashMap<>();

    public MockColourMap() {
        loadShortMapFromFakeCSV();
    }

    private void loadShortMapFromFakeCSV() {
        for(String record : CSV.split("\n")){
            addToMap(record);
        }
    }

    private void addToMap(String record) {
        String[] values = record.split(",");
        String hexColourString = values[VALUE_COLUMN].substring(1);
        int key = Integer.parseInt(hexColourString,16);
        String name = values[NAME_COLUMN];
        colours.put(key, name);
    }

    @Override
    public Map<Integer, String> getColours() {
        return colours;
    }
}
