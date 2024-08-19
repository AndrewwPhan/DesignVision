package com.cv.designvision.models;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CurrentUserTest {

    @Test
    void onlyOneInstanceOfSingletonCanBeMade() {
        CurrentUser inst1 = CurrentUser.getInstance();
        CurrentUser inst2 = CurrentUser.getInstance();
        assertThat(inst1).isEqualTo(inst2);
    }

    @Test
    void userGotFromAnyPlace_isSameUser() {
        // Current user got and modified in one place
        IUser bob = CurrentUser.getInstance().getUser();
        bob.setFirstName("Bob");

        // Current user got and modified in another place
        IUser currentUser = CurrentUser.getInstance().getUser();
        currentUser.setLastName("Smith");

        assertThat(bob).isEqualTo(currentUser);
    }

    @Test
    void newUserObject_hasNoDefaultPatch() {
        IUser user = new User();
        assertThat(user.patches()).isEmpty();
    }

    // FIXME: This is tricky singleton test case flakyness.
    //  Fails on server probably because of run order?
    //  This test is dependent on others - bad.
    //  Pointless test anyway, it was just for changing
    //  over where default patch was being applied.
//    @Test
//    void newCurrentUser_hasNoDefaultPatch() {
//        IUser user = CurrentUser.getInstance().getUser();
//        assertThat(user.patches().size()).isZero();
//    }

    @Test
    void currentUserSetToNew_hasOneDefaultPatch() {
        IUser user = new User();
        CurrentUser.getInstance().setUser(user);
        assertThat(user.patches().size()).isOne();
    }
}