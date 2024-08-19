package com.cv.designvision.controllers;

import com.cv.designvision.Services.UserAuth;
import com.cv.designvision.controllers.panels.Panel;
import com.cv.designvision.controllers.panels.ProfilePanel;
import com.cv.designvision.controllers.panels.SignupPanel;
import com.cv.designvision.models.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.io.IOException;
import java.sql.SQLException;

public class LoginController {
    @FXML
    private TextField emailTxt;
    @FXML
    private PasswordField passwordTxt;
    @FXML
    private Label loginErrorMessage;

    @FXML
    private void handleSignUpButton(){
        new SignupPanel().show();
    }

    @FXML
    private void handleLoginButton() throws SQLException {
        IUserDAO dao = new SqliteUserDao();
        UserAuth auth = new UserAuth(dao);

        // RG non subtle backdoor
        if (emailTxt.getText().equals("test")) {
            emailTxt.setText("test@test.test");
            passwordTxt.setText("Aasdf1234!");
        }

        IUser realUser = auth.validUser(emailTxt.getText(), passwordTxt.getText());

        if (realUser == null){
            showError(auth.getReason());
            return;
        }

        Panel profile = new ProfilePanel();
        auth.setCurrentUser(realUser);
        profile.show();
    }

    private void showError(String message) {
        loginErrorMessage.setStyle("-fx-text-fill: red;");
        loginErrorMessage.setText(message);
    }
}
