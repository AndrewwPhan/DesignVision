package com.cv.designvision.controllers;

import com.cv.designvision.Services.IObserver;
import com.cv.designvision.Services.UserAuth;
import com.cv.designvision.controllers.panels.LoginPanel;
import com.cv.designvision.models.CurrentUser;
import com.cv.designvision.models.IUser;
import com.cv.designvision.models.IUserDAO;
import com.cv.designvision.models.SqliteUserDao;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class ProfileController implements IObserver {
    @FXML
    private TextField userFirstNameTextField;
    @FXML
    private TextField userLastNameTextField;
    @FXML
    private TextField emailTextField;
    @FXML
    private TextField passwordTextField;
    @FXML
    private Label roleLabel;

    IUser user;
    String newPassword;

    @FXML
    public void initialize() {
        CurrentUser.getInstance().register(this);
    }
    @FXML
    public void handleUpdateButton() {
        IUserDAO dao = new SqliteUserDao();
        UserAuth auth = new UserAuth(dao);

        String newEmail = emailTextField.getText();
        newPassword = passwordTextField.getText();

        if(!isPasswordChanged(newPassword)){
            newPassword = user.getPassword();
        }

        if(!auth.isValidEmail(newEmail)||!auth.isValidPassword(newPassword)){
            Popup.show(Popup.ERROR, "Update Error", "Email or Password is invalid");
            return;
        }

        updateUserDetails();
        CurrentUser.getInstance().saveToDb();
        Popup.show(Popup.INFO, "Update Success", "User profile updated successfully.");
    }

    @FXML
    public void handleDeleteButton() {
        new SqliteUserDao().deleteUser(user);

        CurrentUser.getInstance().setLoggedIn(false);
        new LoginPanel().show();
    }

    @FXML
    private void handleLogoutButton() {
        CurrentUser.getInstance().setLoggedIn(false);
        new LoginPanel().show();
    }

    public void setUserProfileDetails() {
        userFirstNameTextField.setText(user.getFirstName());
        userLastNameTextField.setText(user.getLastName());
        emailTextField.setText(user.getEmail());
        String maskedPassword = "*".repeat(user.getPassword().length());
        passwordTextField.setText(maskedPassword);
        roleLabel.setText(user.getRole());
    }

    private void updateUserDetails() {
        user.setFirstName(userFirstNameTextField.getText());
        user.setLastName(userLastNameTextField.getText());
        user.setEmail(emailTextField.getText());
        user.setPassword(newPassword);
    }

    private boolean isPasswordChanged(String password){
        String currentPassword = user.getPassword();
        String maskedPassword = "*".repeat(currentPassword.length());
        if(password.equals(maskedPassword)){
            return false;
        }
        return true;
    }

    @Override
    public <T> void update(T value) {
        user = (IUser) value;
        setUserProfileDetails();
    }
}
