package com.cv.designvision.Services;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class DataCSV implements IDataFile {
    private final String filePath;

    public DataCSV(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public void writeLines(List<String> dataLines) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (String line : dataLines) {
                writer.write(line);
                writer.newLine();
            }
        }
    }

    @Override
    public List<String> readLines() throws IOException {
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        }
        return lines;
    }
}
