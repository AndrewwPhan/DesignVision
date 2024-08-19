package com.cv.designvision.models;

import com.cv.designvision.models.patch.IPatch;
import com.cv.designvision.Services.UserDataList;

import java.util.List;

public interface IUser {
    int getId();

    IUser setId(int id);

    String getFirstName();

    IUser setFirstName(String firstName);

    String getLastName();

    IUser setLastName(String lastName);

    String getEmail();

    IUser setEmail(String email);

    String getPassword();

    IUser setPassword(String password);

    String getFullName();

    String getRole();

    IUser setRole(String role);

    List<Review> getReviews();

    UserDataList<IPatch> patches();
}
