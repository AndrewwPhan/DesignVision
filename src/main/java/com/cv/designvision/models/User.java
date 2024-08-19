package com.cv.designvision.models;

import com.cv.designvision.models.patch.IPatch;
import com.cv.designvision.Services.UserDataList;

import java.util.ArrayList;
import java.util.List;

/**
 * This class implements the {@link IUser} interface and represents a user object
 * within the application. It stores information about the user, including their
 * name, email, password, role (e.g., "user" or "designer"), a list of reviews,
 * and a list of associated patches.
 */
public class User implements IUser {
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String role = CurrentUser.DEFAULT_USER_TYPE;
    private final List<Review> reviews = new ArrayList<>();
    private final UserDataList<IPatch> patches = new UserDataList<>();

    public User() {}

    /**
     * Constructor to create a new User object with only first and last name.
     *
     * @param firstName The user's first name.
     * @param lastName The user's last name.
     */
    public User(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    /**
     * Constructor to create a new User object with all details.
     *
     * @param firstName The user's first name.
     * @param lastName The user's last name.
     * @param email The user's email address.
     * @param password The user's password.
     * @param role The user's role (e.g., "user" or "designer").
     */
    public User(String firstName, String lastName, String email, String password, String role) {
        this(firstName, lastName);
        this.email = email;
        this.password = password;
        this.role = role;
    }

    /**
     * Copy constructor to create a new User object based on an existing one.
     * <p>Note: This implementation performs a shallow copy only,
     * changes to the copied lists will also affect the original user's lists.</p>
     *
     * @param user The IUser object to copy data from.
     */
    public User(IUser user) {
        // NOTE: object cloning is messy.
        //  Not very defensive copying used here
        this(user.getFirstName(), user.getLastName());
        id = user.getId();
        email = user.getEmail();
        password = user.getPassword();
        role = user.getRole();
        //  Mutable lists of mutable things are shallow copied only
        reviews.addAll(user.getReviews());
        patches.addAll(user.patches().iterator());
    }

    // Getters and Setters

    @Override
    public int getId() {
        return id;
    }

    @Override
    public IUser setId(int id) {
        this.id = id;
        return this;
    }

    @Override
    public String getFirstName() {
        return firstName;
    }

    @Override
    public IUser setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    @Override
    public String getLastName() {
        return lastName;
    }

    @Override
    public IUser setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public IUser setEmail(String email) {
        this.email = email;
        return this;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public IUser setPassword(String password) {
        this.password = password;
        return this;
    }

    @Override
    public String getFullName() {
        return String.format("%s %s", firstName, lastName);
    }

    @Override
    public String getRole() {
        return role;
    }

    @Override
    public IUser setRole(String role) {
        this.role = role;
        return this;
    }

    @Override
    public List<Review> getReviews() {
        return reviews;
    }

    public UserDataList<IPatch> patches() {
        return patches;
    }

}
