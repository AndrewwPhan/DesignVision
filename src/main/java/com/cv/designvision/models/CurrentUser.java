package com.cv.designvision.models;

import com.cv.designvision.Services.Observable;
import com.cv.designvision.Services.IObserver;

import com.cv.designvision.controllers.panels.RenderReviewUserPanel;
import com.cv.designvision.controllers.panels.RenderReviewDesignerPanel;

import com.cv.designvision.models.patch.Patch;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

import java.util.Objects;

/**
 * This class implements the Singleton pattern to provide a central access point
 * for the currently logged-in user.
 * It notifies registered observers (implementing the {@link IObserver} interface)
 * when changes occur.
 */
public class CurrentUser extends Observable {
    public static final String DEFAULT_USER_TYPE = "user";
    public static final String DESIGNER_USER_TYPE = "designer";
    private static CurrentUser instance = null;
    private IUser user;
    private final BooleanProperty loggedIn = new SimpleBooleanProperty();

    /**
     * Returns a reference to the `BooleanProperty` object representing the user's
     * login status. This property can be used for binding in UI elements.
     *
     * @return The BooleanProperty object for the logged-in state.
     */
    public BooleanProperty loggedInProperty() {
        return loggedIn;
    }

    /**
     * Private constructor to prevent external instantiation and enforce the
     * Singleton pattern.
     *
     * @param user The initial IUser object representing the logged-in user.
     */
    private CurrentUser(IUser user) {
        this.user = user;
    }

    /**
     * Returns the current instance of {@link CurrentUser}. If no instance exists,
     * a new one is created using a lazy initialization approach.
     *
     * @return The current instance of CurrentUser or a new one if none exists.
     */
    public static CurrentUser getInstance() {
        if (instance == null) {
            instance = new CurrentUser(new User());
        }
        return instance;
    }

    /**
     * @deprecated This method is deprecated in favor of subscribing as an observer
     * to receive updates about the current user. It is recommended to use the observer
     * pattern for a more flexible approach.
     *
     * @return The current IUser object representing the logged-in user.
     */
    @Deprecated
    public IUser getUser() {
        return user;
    }

    /**
     * Sets the current user data and triggers notifications to registered observers.
     * Initializes the active patch object or adds a default if empty.  Sets the
     * appropriate review panel based on the user's role.
     *
     * @param user The IUser object representing the new logged-in user.
     */
    public void setUser(IUser user) {
        this.user = user;
        if (user.patches().size() == 0)
            user.patches().add(new Patch());
        user.patches().setActiveIndex(0);
        setReviewPanel(user);
        notifyObservers();
    }

    /**
     * Private helper method that sets the review panel based on the user's role.
     *
     * @param user The IUser object representing the current user.
     */
    private static void setReviewPanel(IUser user) {
        PanelContainer instance = PanelContainer.getInstance();
        if (Objects.equals(user.getRole(), DESIGNER_USER_TYPE))
            instance.setReviewPanel(new RenderReviewDesignerPanel());
        else
            instance.setReviewPanel(new RenderReviewUserPanel());
    }

    /**
     * Saves the current user's data to the database.
     */
    public void saveToDb() {
        IUserDAO dao = new SqliteUserDao();
        dao.updateUser(user);
    }

    /**
     * Getter method for the logged-in property.
     *
     * @return True if the user is logged in, false otherwise.
     */
    public final boolean getLoggedIn() {
        return loggedIn.get();
    }

    /**
     * Setter method for the logged-in property.
     *
     * @param value True to set the user as logged in, false otherwise.
     */
    public final void setLoggedIn(boolean value) {
        loggedIn.set(value);
    }

    /**
     * Notifies all registered observers about changes in the current user data.
     * List of observers maintained by the {@link Observable} class.
     * {@link IObserver#update(Object)} for each observer,
     * provides {@link IUser} object as a generic type.
     */
    @Override
    public void notifyObservers() {
        for (IObserver observer : observers) {
            observer.update(user);
        }
    }
}
