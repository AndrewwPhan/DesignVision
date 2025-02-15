package com.cv.designvision.models;

import java.util.List;

/**
 * Interface for the User Data Access Object that handles
 * the CRUD operations for the User class with the database.
 */
public interface IUserDAO {
    /**
     * Adds a new user to the database.
     * @param user The user to add.
     */
    void createUser(IUser user);
    /**
     * Updates an existing user in the database.
     * @param user The user to update.
     */
    void updateUser(IUser user);

    /**
     * Deletes a user from the database.
     * @param user The user to delete.
     */
    void deleteUser(IUser user);

    /**
     * Retrieves a user from the database.
     * @param id The id of the user to retrieve.
     * @return The user with the given id, or null if not found.
     */
    IUser readUser(int id);

    /**
     * Retrieves all users from the database.
     * @return A list of all users in the database.
     */
    List<IUser> getAllUsers();


    IUser readUserByEmailAndPassword(IUser user);
}
