package com.cv.designvision.utils;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class ColourMap implements IColourMap{
    public static final int NAME_COLUMN = 0;
    public static final int VALUE_COLUMN = 4;
    private final Map<Integer, String> colours = new HashMap<>();

    public ColourMap(){}

    public ColourMap(String filePath) {
        loadMap(filePath);
    }

    private void loadMap(String filePath) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            String record;
            while ((record = reader.readLine()) != null){
                addToMap(record);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // TODO: this is copied in from mock map,
    //  need to work out if mocking is needed if map is tested separately?
    //  All tests but one now using this implementation.
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
