package com.cv.designvision.controllers;

import com.cv.designvision.Services.UserAuth;
import com.cv.designvision.controllers.panels.LoginPanel;
import com.cv.designvision.controllers.panels.Panel;
import com.cv.designvision.controllers.panels.ProfilePanel;
import com.cv.designvision.models.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;
import java.sql.SQLException;

public class SignupController {
    public static final String SUCCESS_MSG = "Congratulations, your account has been successfully created!";

    @FXML
    private Button ConfirmButton;
    @FXML
    private TextField firstNameTxt;
    @FXML
    private TextField lastNameTxt;
    @FXML
    private TextField emailTxt;
    @FXML
    private PasswordField passwordTxt;
    @FXML
    private PasswordField password2txt;
    @FXML
    private CheckBox userCheckbox;
    @FXML
    private CheckBox designerCheckbox;
    @FXML
    private Label signupErrorMessage;

    @FXML
    private void handleConfirm() throws IOException, SQLException {
        IUser user = buildUserFromInputs();
        IUserDAO dao = new SqliteUserDao();
        UserAuth auth = new UserAuth(dao);

        if(!auth.isValidEmail(emailTxt.getText())) {
            showError(auth.getReason());
            return;
        }
        if (!auth.isValidPassword(passwordTxt.getText(), password2txt.getText())) {
            showError(auth.getReason());
            return;
        }

        dao.createUser(user);
        IUser realUser = auth.validUser(emailTxt.getText(), passwordTxt.getText());

        if (realUser == null){
            showError(auth.getReason());
            return;
        }

        Panel profile = new ProfilePanel();
        auth.setCurrentUser(realUser);
        profile.show();
    }

    @FXML
    private void handleGoback(){
        new LoginPanel().show();
    }

    private void showError(String message) {
        signupErrorMessage.setStyle("-fx-text-fill: red;");
        signupErrorMessage.setText(message);
    }

    private IUser buildUserFromInputs() {
        // TODO: input error handling
        return new User(
                firstNameTxt.getText(),
                lastNameTxt.getText(),
                emailTxt.getText(),
                passwordTxt.getText(),
                getRole()
        );
    }

    public String getRole() {
        // TODO: should be const from somewhere, maybe singletons?
        if (designerCheckbox.isSelected())
            return CurrentUser.DESIGNER_USER_TYPE;
        return CurrentUser.DEFAULT_USER_TYPE;
    }
}
