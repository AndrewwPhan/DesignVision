package com.cv.designvision.models;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SqliteUserDao implements IUserDAO {
    private final Connection connection;

    /**
     * Default constructor that connects to real project db
     */
    public SqliteUserDao() {
        connection = SqliteConnection.getInstance();
        init();
    }

    /**
     * Constructor that uses a different db connection from main project db
     *
     * @param connection Connection to use for this instance
     */
    public SqliteUserDao(Connection connection) {
        this.connection = connection;
        init();
    }

    private void init() {
        createUserTableIfNone();
    }

    private void createUserTableIfNone() {
        try (Statement statement = connection.createStatement()) {
            statement.execute(
                    "CREATE TABLE IF NOT EXISTS users (" +
                            "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                            "firstName VARCHAR, " +
                            "lastName VARCHAR, " +
                            "email VARCHAR NOT NULL, " +
                            "password VARCHAR NOT NULL, " +
                            "role VARCHAR)"
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void createUser(IUser user) {
        try (PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO " +
                        "users (firstName, lastName, email, password, role) " +
                        "VALUES (?,?,?,?,?)")) {
            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getLastName());
            statement.setString(3, user.getEmail());
            statement.setString(4, user.getPassword());
            statement.setString(5, user.getRole());
            statement.executeUpdate();
            ResultSet keys = statement.getGeneratedKeys();
            if (keys.next()) user.setId(keys.getInt(1));
            else throw new SQLException("Did not get created user id");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        new ReviewTable(connection).createData(user);
        new PatchTable(connection).createData(user);
    }

    @Override
    public IUser readUser(int id) {
        try (PreparedStatement statement = connection.prepareStatement(
                "SELECT * FROM users WHERE id = ?")) {
            statement.setInt(1, id);
            return buildUserFromResult(statement.executeQuery());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public IUser readUserByEmailAndPassword(IUser user) {
        try (PreparedStatement statement = connection.prepareStatement(
                "SELECT * FROM users WHERE email = ? AND password = ?")) {
            statement.setString(1, user.getEmail());
            statement.setString(2, user.getPassword());
            return buildUserFromResult(statement.executeQuery());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private IUser buildUserFromResult(ResultSet sqlUserResult) {
        try {
            if (!sqlUserResult.next()) return null;
            IUser user = new User(
                    sqlUserResult.getString("firstName"),
                    sqlUserResult.getString("lastName"),
                    sqlUserResult.getString("email"),
                    sqlUserResult.getString("password"),
                    sqlUserResult.getString("role")
            );
            user.setId(sqlUserResult.getInt("id"));
            new ReviewTable(connection).readData(user);
            new PatchTable(connection).readData(user);
            return user;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateUser(IUser user) {
        // TODO: Error handling if user id wrong? blank? password?
        // TODO: have to fix these bug hiding try catches
        // TODO: still need to close the connection too... its going to bite!
        try (PreparedStatement statement = connection.prepareStatement(
                "UPDATE users " +
                        "SET firstName = ?, " +
                        "lastName = ?, " +
                        "email = ?, " +
                        "password = ?, " +
                        "role = ?" +
                        "WHERE id = ?")) {
            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getLastName());
            statement.setString(3, user.getEmail());
            statement.setString(4, user.getPassword());
            statement.setString(5, user.getRole());
            statement.setInt(6, user.getId());
            statement.executeUpdate();

            new ReviewTable(connection).updateData(user);
            new PatchTable(connection).updateData(user);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteUser(IUser user) {
        new ReviewTable(connection).deleteData(user.getId());
        new PatchTable(connection).deleteData(user.getId());
        try (PreparedStatement statement = connection.prepareStatement(
                "DELETE FROM users WHERE id = ?")) {
            statement.setInt(1, user.getId());
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<IUser> getAllUsers() {
        try (Statement statement = connection.createStatement()){
            ResultSet result = statement.executeQuery("SELECT * FROM users");
            List<IUser> users = new ArrayList<>();
            IUser user;
            while ((user = buildUserFromResult(result)) != null) {
                users.add(user);
            }
            return users;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
