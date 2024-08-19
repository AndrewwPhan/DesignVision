package com.cv.designvision.Services;

import com.cv.designvision.models.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

class UserAuthTest {
    public static final String MIN_EMAIL_REQS = "a@a.a";
    public static final String MIN_PASSWORD_REQS = "Abcdef7@";
    public static final String PW_7_CHARS = "Abcde6@";
    public static final String PW_NO_SPECIAL_CHAR = "Abcdef78";
    public static final String PW_NO_UPPERCASE = "abcdef7@";

    // Requirements:
    // - should authenticate user
    // - should be able to query something to know if existing user
    //   - user should exist in db
    // - should validate a password
    //     - min 8 chars
    //     - at least 1 special chars
    //     - not null..... data validation - user entry (never trust user)
    //     - at least one uppercase
    //     - at least one number

    UserAuth auth;
    IUserDAO dao;

    @BeforeEach
    void setup() {
        dao = new MockUserDao();
        auth = new UserAuth(dao);
    }

    @Test
    void nullPassword_shouldFail() {
        assertThat(auth.isValidPassword(null)).isFalse();
    }

    @Test
    void emptyPassWord_shouldFail() {
        assertThat(auth.isValidPassword("")).isFalse();
    }

    @Test
    void passwordWithMinRequirements_shouldPass() {
        assertThat(auth.isValidPassword(MIN_PASSWORD_REQS)).isTrue();
    }

    @Test
    void lessThan8chars_shouldFail() {
        assertThat(auth.isValidPassword(PW_7_CHARS)).isFalse();
    }


    @Test
    void passwordHasNoSpecialChar_shouldFail() {
        assertThat(auth.isValidPassword(PW_NO_SPECIAL_CHAR)).isFalse();
    }

    @Test
    void passwordHasNoUppercase_shouldFail() {
        assertThat(auth.isValidPassword(PW_NO_UPPERCASE)).isFalse();
    }

    @Test
    void failedPasswordValidation_storesReasonForFailure() {
        auth.isValidPassword("");
        assertThat(auth.getReason()).isNotBlank();
    }

    @Test
    void passwordValidated_reasonIsBackToNull() {
        auth.isValidPassword(MIN_PASSWORD_REQS);
        assertThat(auth.getReason()).isEmpty();
    }

    @Test
    void passwordMatchesOtherPassword_isValid() {
        assertThat(auth.isValidPassword(MIN_PASSWORD_REQS, MIN_PASSWORD_REQS)).isTrue();
        assertThat(auth.getReason()).isEmpty();
    }

    @Test
    void nonMatchingPasswords_notValid() {
        boolean valid = auth.isValidPassword(MIN_PASSWORD_REQS, MIN_PASSWORD_REQS + "x");
        assertThat(valid).isFalse();
        assertThat(auth.getReason()).isNotNull();
    }

    @Test
    void userNotInInDB_ShouldFail() throws SQLException {
        String email = MIN_EMAIL_REQS;
        String password = "superSecret@1";
        assertThat(auth.validUser(email, password)).isNull();
    }

    @Test
    void userIsInDbWithSamePW_authShouldPass() throws SQLException {

        String email = MIN_EMAIL_REQS;
        String password = "superSecret@1";
        // TODO: use new fluid assignment
        User user = new User(
                "a", "b",
                email, password,
                "");
        dao.createUser(user);

        assertThat(auth.validUser(email, password)).isNotNull();
    }

    @Test
    void authenticatorShouldSetCurrentUser() throws SQLException {
        // TODO: Design: Login current user with authentication.
        //  - [ ] auth could extend user singleton for "security"?
        //  - [ ] then, the only access to set current user instance is via auth?

        IUser realUser = new User()
                .setEmail(MIN_EMAIL_REQS)
                .setPassword("superSecret@1");
        // TODO: add userExists(user) to dao
        dao.createUser(realUser); // TODO: should only be exposed to auth
        boolean success = auth.setCurrentUser(realUser);

        assertThat(success).isTrue();
        IUser currentUser = CurrentUser.getInstance().getUser();
        assertThat(currentUser.getEmail()).isEqualTo(realUser.getEmail());
    }

    @Test
    void loggedInUser_hasLoggedInFlag() throws SQLException {
        CurrentUser instance = CurrentUser.getInstance();
        IUser realUser = new User()
                .setEmail(MIN_EMAIL_REQS)
                .setPassword("superSecret@1");
        dao.createUser(realUser);
        auth.setCurrentUser(realUser);
        assertThat(instance.getLoggedIn()).isTrue();
    }

    @Test
    void nonLoggedInUser_flagIsNotSet() throws SQLException {
        CurrentUser instance = CurrentUser.getInstance();
        auth.setCurrentUser(new User());
        assertThat(instance.getLoggedIn()).isFalse();
    }

    @Test
    void emailWithMinRequirements_shouldPass() {
        auth.isValidEmail(MIN_EMAIL_REQS);
        assertThat(auth.isValidEmail(MIN_EMAIL_REQS)).isTrue();
    }
    @Test
    void emailWithNoNameShouldFail() {
        assertThat(auth.isValidEmail("@a.aa")).isFalse();
    }
    @Test
    void emailWithNoATShouldFail() {
        assertThat(auth.isValidEmail("eric323gmail.com")).isFalse();
    }

    @Test
    void emailWithNoDotShouldFail() {
        assertThat(auth.isValidEmail("eric323@gmailcom")).isFalse();
    }
    @Test
    void emailWithNoDomainNameShouldFail() {
        assertThat(auth.isValidEmail("eric@.a")).isFalse();
    }
    @Test
    void emailWithNoTopLevelDomainNameShouldFail() {
        assertThat(auth.isValidEmail("eric@abc.")).isFalse();
    }

    @Test
    void emailWithNoNumberShouldPass() {
        assertThat(auth.isValidEmail("eric@gmail.a")).isTrue();
    }
    @Test
    void emailWithNumberShouldPass() {
        assertThat(auth.isValidEmail("eric3@gmail.a")).isTrue();
    }

    @Test
    void testEmailShouldPass(){
        String testEmail = "test@test.test";
        assertThat(auth.isValidEmail(testEmail)).isTrue();
    }


}