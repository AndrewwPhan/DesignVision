package com.cv.designvision.controllers;

import com.cv.designvision.Services.IObserver;
import com.cv.designvision.models.CurrentUser;
import com.cv.designvision.models.IUser;
import com.cv.designvision.models.Review;
import com.cv.designvision.models.User;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.util.List;

public abstract class RenderReviewController implements IObserver {

    private static final int NUM_REVIEWS = 6;

    @FXML
    public void initialize() throws IOException {
        CurrentUser.getInstance().register(this);
    }

    protected Review review1 = new Review();
    protected Review review2 = new Review();
    protected Review review3 = new Review();
    protected Review review4 = new Review();
    protected Review review5 = new Review();
    protected Review review6 = new Review();

    IUser user = new User();

    @FXML
    private GridPane imageGrid1;
    @FXML
    private GridPane imageGrid2;
    @FXML
    private GridPane imageGrid3;
    @FXML
    private GridPane imageGrid4;
    @FXML
    private GridPane imageGrid5;
    @FXML
    private GridPane imageGrid6;

    @FXML
    private TextField Image_1_Text_Entry;
    @FXML
    private TextField Image_2_Text_Entry;
    @FXML
    private TextField Image_3_Text_Entry;
    @FXML
    private TextField Image_4_Text_Entry;
    @FXML
    private TextField Image_5_Text_Entry;
    @FXML
    private TextField Image_6_Text_Entry;


    protected void updateReviews() {

        List<Review> reviews = user.getReviews();
        int required = NUM_REVIEWS - reviews.size();
        for (int i = 0; i < required; i++) {
            reviews.add(new Review());
        }
        review1 = reviews.get(0);
        review2 = reviews.get(1);
        review3 = reviews.get(2);
        review4 = reviews.get(3);
        review5 = reviews.get(4);
        review6 = reviews.get(5);

        clearUIView(imageGrid1);
        clearUIView(imageGrid2);
        clearUIView(imageGrid3);
        clearUIView(imageGrid4);
        clearUIView(imageGrid5);
        clearUIView(imageGrid6);

        populateUIView();

    }

    @FXML
    protected void populateUIView() {
        //Node node = imageGrid1.getChildren().get(0);
        List<Review> reviews = user.getReviews();
        for (int i = 0; i < 9; i++) {
            Node node = imageGrid1.getChildren().get(i);
            if (node.getClass() == Button.class) {
                int result = reviews.get(0).getValue(i);
                String ch = result > 0 ? "X" : "";
                ((Button) node).setText(ch);
            }
        }
        for (int i = 0; i < 9; i++) {
            Node node = imageGrid2.getChildren().get(i);
            if (node.getClass() == Button.class) {
                int result = reviews.get(1).getValue(i);
                String ch = result > 0 ? "X" : "";
                ((Button) node).setText(ch);
            }
        }
        for (int i = 0; i < 9; i++) {
            Node node = imageGrid3.getChildren().get(i);
            if (node.getClass() == Button.class) {
                int result = reviews.get(2).getValue(i);
                String ch = result > 0 ? "X" : "";
                ((Button) node).setText(ch);
            }
        }
        for (int i = 0; i < 9; i++) {
            Node node = imageGrid4.getChildren().get(i);
            if (node.getClass() == Button.class) {
                int result = reviews.get(3).getValue(i);
                String ch = result > 0 ? "X" : "";
                ((Button) node).setText(ch);
            }
        }
        for (int i = 0; i < 9; i++) {
            Node node = imageGrid5.getChildren().get(i);
            if (node.getClass() == Button.class) {
                int result = reviews.get(4).getValue(i);
                String ch = result > 0 ? "X" : "";
                ((Button) node).setText(ch);
            }
        }
        for (int i = 0; i < 9; i++) {
            Node node = imageGrid6.getChildren().get(i);
            if (node.getClass() == Button.class) {
                int result = reviews.get(5).getValue(i);
                String ch = result > 0 ? "X" : "";
                ((Button) node).setText(ch);
            }
        }

        Image_1_Text_Entry.setText(review1.getComment());
        Image_2_Text_Entry.setText(review2.getComment());
        Image_3_Text_Entry.setText(review3.getComment());
        Image_4_Text_Entry.setText(review4.getComment());
        Image_5_Text_Entry.setText(review5.getComment());
        Image_6_Text_Entry.setText(review6.getComment());

    }

    @FXML
    private void clearUIView(GridPane grid) {
        for (Node node : grid.getChildren()) {
            if (node.getClass() == Button.class) {
                ((Button) node).setText("");
            }
        }
        Image_1_Text_Entry.setText("");
        Image_2_Text_Entry.setText("");
        Image_3_Text_Entry.setText("");
        Image_4_Text_Entry.setText("");
        Image_5_Text_Entry.setText("");
        Image_6_Text_Entry.setText("");
    }

    @Override
    public <T> void update(T newUser) {
        user = (IUser) newUser;
        updateReviews();
    }
}
