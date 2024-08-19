package com.cv.designvision.models.patch;

import com.cv.designvision.Services.IDataFile;
import com.cv.designvision.controllers.Popup;
import com.cv.designvision.Services.UserDataList;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * PatchSetManager handles exporting and importing patch sets.
 * Depends on injected implementation of data access object.
 */
public class PatchSetManager {
    private final IDataFile dataIO;

    /**
     * Constructs a PatchSetManager with the provided data access object.
     *
     * @param dataIO The implementation of `IUserDataIO` used for data access.
     */
    public PatchSetManager(IDataFile dataIO) {
        this.dataIO = dataIO;
    }


    /**
     * Exports a list of patches to a CSV (Comma-Separated Values) format.
     * Writes encoded data of each patch to the data storage object.
     *
     * @param patches The list of patches to be exported.
     */
    public void exportToCSV(UserDataList<IPatch> patches) throws IOException {
        List<String> data = new ArrayList<>();
        for (IPatch patch : patches)
            data.add(patch.getEncoded());
        dataIO.writeLines(data);
    }

    /**
     * Imports a list of patches from a CSV format data storage.
     * Creates a new {@link IPatch} for each line.
     *
     * @return A UserDataList containing the imported patches
     * or empty list on error.
     */
    public UserDataList<IPatch> importFromCSV() throws IOException {
        UserDataList<IPatch> patches = new UserDataList<>();
        List<String> lines = dataIO.readLines();
        if (lines.size() == 1 && Objects.equals(lines.get(0), ""))
            return null;
        for (String line : lines) {
            if (line.isEmpty()) continue;
            // TODO: error checking on reading data into patch
            patches.add(new Patch(line));
        }
        return patches;
    }
}
