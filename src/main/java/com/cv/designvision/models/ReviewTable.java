package com.cv.designvision.models;

import java.sql.*;

public class ReviewTable extends DataTable {

    public ReviewTable(Connection connection) {
        this.tableName = "reviews";
        this.connection = connection;
        createTableIfNone();
    }

    protected void createTableIfNone() {
        try (Statement statement = connection.createStatement()) {
            statement.execute(
                    "CREATE TABLE IF NOT EXISTS " + tableName + " (" +
                            "user_id INTEGER, " +
                            "review VARCHAR, " +
                            "comment VARCHAR, " +
                            "FOREIGN KEY(user_id) REFERENCES users(id))"
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void createData(IUser user) {
        for (Review review : user.getReviews()) {
            try (PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO " + tableName +
                            " (review, comment, user_id) "
                            + "VALUES (?,?,?)")) {
                statement.setString(1, review.getEncoded());
                statement.setString(2, review.getComment());
                statement.setInt(3, user.getId());
                statement.execute();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void readData(IUser user) {
        try (ResultSet rs = getResultSet(user)) {
            while (rs.next()) {
                Review review = new Review();
                review.setEncoded(rs.getString("review"));
                review.setComment(rs.getString("comment"));
                user.getReviews().add(review);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
