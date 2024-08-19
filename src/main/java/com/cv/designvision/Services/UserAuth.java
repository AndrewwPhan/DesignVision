package com.cv.designvision.Services;

import com.cv.designvision.models.CurrentUser;
import com.cv.designvision.models.IUser;
import com.cv.designvision.models.IUserDAO;
import com.cv.designvision.models.User;

import java.sql.SQLException;

public class UserAuth {
    public static final String INVALID_PWD_MSG =
            "Password must contain at least 8 characters, " +
                    "including upper/lowercase, " +
                    "special character and numbers.";
    public static final String NO_MATCH_MSG = "Passwords do not match.";

    /**
     * Email validation pattern requirements:
     * - At least one character before the '@' symbol
     * - At least one character for the domain part followed by a '.'
     * - At least one character for the Top-level Domain name.
     */
    public static final String INVALID_EMAIL_MSG = "Invalid email address.";
    public static final String INCORRECT_EMAIL_OR_PASS = "Incorrect email or password";
    private static final int MIN_LENGTH = 8;
    private static final String PASSWORD_PATTERN =
            "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])" +
                    "(?=.*[@#$%^&+=!])" +
                    "(?=\\S+$).{" +
                    MIN_LENGTH + ",}$";

    private static final String EMAIL_PATTERN = "^[^\\s@]+@[^\\s@]+\\.[^\\s@.]+$";

    private final IUserDAO dao;
    private String failReason = "";

    public UserAuth(IUserDAO dao) {
        this.dao = dao;
    }

    public boolean isValidEmail(String email) {
        failReason = "";
        if (email == null || !email.matches(EMAIL_PATTERN)) {
            failReason = INVALID_EMAIL_MSG;
            return false;
        }
        return true;
    }

    public boolean isValidPassword(String password) {
        failReason = "";
        if (password == null || !password.matches(PASSWORD_PATTERN)) {
            failReason = INVALID_PWD_MSG;
            return false;
        }
        return true;
    }

    public boolean isValidPassword(String pass1, String pass2) {
        failReason = "";
        if (!pass1.equals(pass2)) {
            failReason = NO_MATCH_MSG;
            return false;
        }
        return isValidPassword(pass1);
    }

    public IUser validUser(String email, String password) {
        failReason = "";
        if (!isValidEmail(email)) return null;
        if (!isValidPassword(password)) return null;
        IUser user = dao.readUserByEmailAndPassword(new User().setEmail(email).setPassword(password));
        if (user == null) failReason = INCORRECT_EMAIL_OR_PASS;
        return user;
    }

    public String getReason() {
        return failReason;
    }

    public boolean setCurrentUser(IUser user) throws SQLException {

        // TODO: needs work on "security" and design intent
        if (validUser(user.getEmail(), user.getPassword()) == null) {
            // instance.setUser(null); // TODO: why was this being done?
            CurrentUser.getInstance().setLoggedIn(false);
            return false;
        }

        CurrentUser.getInstance().setUser(user);
        CurrentUser.getInstance().setLoggedIn(true);
        return true;
    }
}
