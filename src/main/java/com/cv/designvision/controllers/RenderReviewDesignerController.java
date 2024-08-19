package com.cv.designvision.controllers;

import com.cv.designvision.models.CurrentUser;
import com.cv.designvision.models.IUser;
import com.cv.designvision.models.SqliteUserDao;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class RenderReviewDesignerController extends RenderReviewController {

    @FXML private ComboBox userSelectionMenu;

    Map<String, Integer> userMap = new HashMap<>();

    /**
     * TODO: Add labels to controller and call through FXML, instead of doing everything in FXML
     */

    @Override
    public void initialize() throws IOException {
        super.initialize();
        List<IUser> users = new SqliteUserDao().getAllUsers();
        ObservableList listedUsers = userSelectionMenu.getItems();
        for (IUser user: users) {
            if (Objects.equals(user.getRole(), CurrentUser.DESIGNER_USER_TYPE)) continue;
            String fullName = user.getFullName();
            int id = user.getId();
            userMap.put(fullName, id);
            listedUsers.add(fullName);
        }
    }

    @FXML
    public void selectUser(ActionEvent event) {
        String userName = ((ComboBox) event.getSource()).getValue().toString();
        update(new SqliteUserDao().readUser(userMap.get(userName)));
    }
}
