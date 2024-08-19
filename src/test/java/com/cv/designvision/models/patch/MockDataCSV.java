package com.cv.designvision.models.patch;

import com.cv.designvision.Services.IDataFile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MockDataCSV implements IDataFile {
    public StringBuilder fileData = new StringBuilder();
    private boolean deliberatelyFailImport = false;

    @Override
    public void writeLines(List<String> dataLines) {
        fileData = new StringBuilder();
        for (String line : dataLines){
            fileData.append(line).append("\n");
        }
    }

    @Override
    public List<String> readLines() {
        if (deliberatelyFailImport)
            return new ArrayList<>() {{add("");}};
        return Arrays.stream(fileData.toString().split("\n")).toList();
    }

    public void add(String line) {
        fileData.append(line).append("\n");
    }

    public void setImportToFail() {
        deliberatelyFailImport = true;
    }
}
