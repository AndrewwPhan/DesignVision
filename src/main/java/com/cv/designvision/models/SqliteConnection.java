package com.cv.designvision.models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SqliteConnection {
    private static Connection instance = null;

    private SqliteConnection() {
        // TODO: rebuild databases once lag and parameters are fixed

        // Slow
        String url = "jdbc:sqlite:DesignVision.db";

        // FIXME: This is not how to add parameters, need it for jdbc speedup
        //String url = "jdbc:sqlite:DesignVision.db;sendStringParametersAsUnicode=false";

        try {
            instance = DriverManager.getConnection(url);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Connection getInstance() {
        if (instance == null) {
            new SqliteConnection();  // TODO: hate this
        }
        return instance;
    }

}
