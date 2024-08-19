package com.cv.designvision.Services;

import java.io.IOException;
import java.util.List;

/**
 * Methods for data access operations.
 */
public interface IDataFile {
    /**
     * Writes a list of data lines to the data storage.
     *
     * @param dataLines The list of strings to be written.
     * @throws IOException if error writing file
     */
    void writeLines(List<String> dataLines) throws IOException;
    /**
     * Reads a list of data lines from the data storage.
     *
     * @return A list of strings containing the data lines.
     * @throws IOException if error reading file
     */
    List<String> readLines() throws IOException ;
}
