package com.cv.designvision.Services;

import com.cv.designvision.models.CurrentUser;
import com.cv.designvision.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ObserverTest {
    CurrentUser observable;
    MockObserver observer;

    @BeforeEach
    void setup(){
        observable = CurrentUser.getInstance();
        observer = new MockObserver();
        observable.register(observer);
    }

    @Test
    void observerGetsNotifiedOfChangedUser() {
        // "login" new user
        User bob = new User();
        observable.setUser(bob);

        // Ensure that observer has been notified
        assertThat(observer.getObserved()).isEqualTo(bob);
    }

    @Test
    void unregisteredObserver_noLongerGetsNotified() {
        // "login" new user while observer is watching
        User bob = new User();
        observable.setUser(bob);

        // Unregister observable then logged in new user
        observable.unregister(observer);
        observable.setUser(new User());

        // Ensure that the observer no longer gets the update
        assertThat(observer.getObserved()).isEqualTo(bob);
    }
}