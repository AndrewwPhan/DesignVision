package com.cv.designvision.controllers;

import com.cv.designvision.Services.IObserver;
import com.cv.designvision.models.CurrentUser;
import com.cv.designvision.models.IUser;
import com.cv.designvision.models.Review;
import com.cv.designvision.models.User;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RenderReviewUserController extends RenderReviewController implements IObserver {
    /**
     * TODO: Add labels to controller and call through FXML, instead of doing everything in FXML
     */

    @FXML
    private void cellClick(Event event){

        Button btn = (Button) event.getSource();

        int cellID = Integer.parseInt(btn.getId().substring(5));
        int cellModifier = getCellModifier(cellID);
        int idx = cellID - cellModifier;

        Map <Integer, Review> reviewMap = new HashMap<>();
        reviewMap.put(1,review1);
        reviewMap.put(10,review2);
        reviewMap.put(19,review3);
        reviewMap.put(28,review4);
        reviewMap.put(37,review5);
        reviewMap.put(46,review6);
        Review review = reviewMap.get(cellModifier);

        setCell(idx, review, btn);

    }

    public void setCell(int idx, Review review, Button btn) {
        if (review.getValue(idx) > 0) {
            btn.setText("");
            review.setTo(idx, 0);
        } else {
            btn.setText("X");
            review.setTo(idx, 1);
        }
        System.out.println(review);
    }

    private static int getCellModifier(int cellID) {
        int cellModifier = 0;
        /*
        Calculate modifier based on cell number - (9*image number)
         */
        if (cellID > 0 && cellID < 10){
            cellModifier = 1;
        } else if (cellID > 9 && cellID <= 18) {
            cellModifier = 10;
        } else if (cellID > 18 && cellID <= 27) {
            cellModifier = 19;
        } else if (cellID > 27 && cellID <= 36) {
            cellModifier = 28;
        } else if (cellID > 36 && cellID <= 45) {
            cellModifier = 37;
        } else if (cellID > 45 && cellID <= 54) {
            cellModifier = 46;
        }
        System.out.println("The cell modifier is " + cellModifier);
        return cellModifier;
    }

    @FXML
    private void onComment(Event event){
        String comment = ((TextField) event.getSource()).getText();
        String id = (((TextField) event.getSource()).getId());
        int imageID = getImageNumberFromID(id);
        if (imageID == 1) {
            review1.setComment(comment);
        } else if (imageID == 2) {
            review2.setComment(comment);
        } else if (imageID == 3) {
            review3.setComment(comment);
        } else if (imageID == 4) {
            review4.setComment(comment);
        } else if (imageID == 5) {
            review5.setComment(comment);
        } else if (imageID == 6) {
            review6.setComment(comment);
        }
    }

    public int getImageNumberFromID(String imageGridID) {
        // Would not work with double-digit images. Only 6 images are planned,
        // Regex required for 10+ images
        return Integer.parseInt(imageGridID.substring(6,7));
    }


}
