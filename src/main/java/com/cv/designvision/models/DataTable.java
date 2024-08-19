package com.cv.designvision.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class DataTable {
    protected String tableName;
    Connection connection;

    protected abstract void createTableIfNone();

    public abstract void createData(IUser user);

    public abstract void readData(IUser user);

    public void updateData(IUser user) {
        deleteData(user.getId());
        createData(user);
    }

    public void deleteData(int userId) {
        try (PreparedStatement statement = connection.prepareStatement(
                "DELETE FROM " + tableName + " WHERE user_id=?")
        ) {

            statement.setInt(1, userId);
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    protected ResultSet getResultSet(IUser user) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM " + tableName + " WHERE user_id = ?");
            statement.setInt(1, user.getId());
            return statement.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
