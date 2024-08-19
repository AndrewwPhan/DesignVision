package com.cv.designvision.models.patch;

import com.cv.designvision.Services.UserDataList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

class PatchSetManagerTest {
    private static final int LINE_COUNT = 5;
    private static final String ENCODING =
            "0x00000000, 0xff0000ff, 0x00ff00ff, 0x0000ffff";
    private MockDataCSV mock;
    private PatchSetManager setManager;

    @BeforeEach
    void setup(){
        mock =  new MockDataCSV();
        for (int i = 0; i< LINE_COUNT; i++) mock.add(ENCODING);
        setManager = new PatchSetManager(mock);
    }

    @Test
    void exportsSinglePatchToCSVAsOneLine() throws IOException {
        UserDataList<IPatch> patches = new UserDataList<>();
        patches.add(new Patch(ENCODING));

        setManager.exportToCSV(patches);

        assertThat(mock.fileData.toString()).isEqualTo(ENCODING + "\n");
    }

    // TODO: test thrown error

    @Test
    void exportsPatchCountMatchesNumberOfLines() throws IOException {
        UserDataList<IPatch> patches = new UserDataList<>();
        final int patchCount = 10;
        for (int i = 0; i< patchCount; i++)
            patches.add(new Patch(ENCODING));

        setManager.exportToCSV(patches);

        assertThat(mock.fileData.toString().split("\n"))
                .hasSize(patchCount);
    }

    @Test
    void importsPatchesFromDataLines() throws IOException {
        UserDataList<IPatch> patches = setManager.importFromCSV();
        assertThat(patches.getActive().getEncoded()).isEqualTo(ENCODING);
    }

    @Test
    void importsLineCountNumberOfPatches() throws IOException {
        UserDataList<IPatch> patches = setManager.importFromCSV();
        assertThat(patches).hasSize(LINE_COUNT);
    }

    @Test
    void importsFailureReturnsNull() throws IOException {
        mock.setImportToFail();
        UserDataList<IPatch> patches = setManager.importFromCSV();
        assertThat(patches).isNull();
    }

    @Test
    void importsEachPatchWithEncodedData() throws IOException {
        UserDataList<IPatch> patches = setManager.importFromCSV();
        for (IPatch patch : patches) {
            assertThat(patch.getEncoded()).isEqualTo(ENCODING);
        }
    }

//    @Disabled("Only used locally for writing and reading real file")
//    @Test
//    void exportsToRealFile() {
//        UserDataList<IPatch> patches = new UserDataList<>();
//        for (int i = 0; i< LINE_COUNT; i++)
//            patches.add(new FakePatch(ENCODING));
//
//        // TODO: should be using more reliable temp file:
//        //  File tempFile = File.createTempFile("userData", ".csv");
//        //  UserDataCSV userDataCSV = new UserDataCSV(tempFile.getAbsolutePath());
//
//        String filePath = "testExport.csv";
//        IUserDataIO userDataCSV = new UserDataCSV(filePath);
//        PatchSetManager setManager = new PatchSetManager(userDataCSV);
//        setManager.exportToCSV(patches);
//
//        UserDataList<IPatch> readPatches = setManager.importFromCSV();
//        assertThat(readPatches).hasSize(LINE_COUNT);
//        assertThat(readPatches.getActive().getEncoded())
//                .isEqualTo(patches.getActive().getEncoded());
//
//        File file = new File(filePath);
//        file.delete();
//    }
}