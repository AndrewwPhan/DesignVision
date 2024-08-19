package com.cv.designvision.models;

import com.cv.designvision.models.patch.Patch;
import com.cv.designvision.models.patch.IPatch;

import java.sql.*;

public class PatchTable extends DataTable {

    public PatchTable(Connection connection) {
        this.tableName = "patches";
        this.connection = connection;
        createTableIfNone();
    }

    protected void createTableIfNone() {
        try (Statement statement = connection.createStatement()) {
            statement.execute(
                    "CREATE TABLE IF NOT EXISTS " + tableName + " (" +
                            "user_id INTEGER, " +
                            "patchCode VARCHAR, " +
                            "FOREIGN KEY(user_id) REFERENCES users(id))"
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void createData(IUser user) {
        for (IPatch patch : user.patches()) {
            try (PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO " + tableName +
                            " (patchCode, user_id) " +
                            "VALUES (?,?)")) {
                statement.setString(1, patch.getEncoded());
                statement.setInt(2, user.getId());
                statement.execute();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void readData(IUser user) {
        try (ResultSet rs = getResultSet(user)) {
            while (rs.next()) {
                IPatch patch = new Patch();
                patch.setColoursFromCode(rs.getString("patchCode"));
                user.patches().add(patch);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
