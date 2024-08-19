package com.cv.designvision.models;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class UserTest {
    @Test
    public void fullName_isConcatenationOfFirstAndLastName() {
        IUser user = new User()
                .setFirstName("John")
                .setLastName("Doe");
        assertThat(user.getFullName()).isEqualTo("John Doe");
    }

    @Test
    void fluidStyleComposition_userIsCreatedWithProperties() {
        IUser user = new User()
                .setFirstName("User")
                .setLastName("Name");
        assertThat(user.getFullName()).isEqualTo("User Name");
    }

    @Test
    void userCanCopyFromOther(){
        IUser bob = new User()
                .setFirstName("Bob")
                .setLastName("Jones");
        IUser bobCopy = new User(bob);
        assertThat(bob.getFullName()).isEqualTo(bobCopy.getFullName());
    }

    // User Review Management -------------------------------------
    // TODO: code smell. Is managing reviews and patches up to the user class?

    @Test
    void reviewListOfCopiedUserIsShallowCopied(){
        IUser bob = new User()
                .setFirstName("Bob")
                .setLastName("Jones");
        Review review = new Review();
        review.setTo(0, 1);
        bob.getReviews().add(review);

        IUser bobCopy = new User(bob);
        bobCopy.getReviews().add(new Review());

        assertThat(bobCopy).isNotEqualTo(bob);
        assertThat(bobCopy.getReviews().get(0)).isEqualTo(review);
        assertThat(bobCopy.getReviews()).isNotEqualTo(bob.getReviews());
    }
}
