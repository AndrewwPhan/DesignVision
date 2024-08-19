package com.cv.designvision.controllers;

import com.cv.designvision.utils.ColourNamer;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;


public class AccessibilityController {
    @FXML private ColorPicker valuePicker;
    @FXML private Rectangle chosenColourPad;
    @FXML private Rectangle closestColourPad;
    @FXML private Label closestNameLbl;
    @FXML private Label chosenValueLbl;
    @FXML private Label closestValueLbl;

    ColourNamer colours;
    @FXML
    public void initialize() {
        colours = new ColourNamer();
    }

    @FXML
    private void onColourValuePick(){
        Color chosenColour = valuePicker.getValue();
        int chosenValue = chosenColour.hashCode();

        // TODO: namer should just accept color object
        int chosenValueRGB = chosenValue >> 8 & 0xFFFFFF;
        String closestName = colours.getName(chosenValueRGB);
        int closestValue = colours.getValue();
        Color closestColor = toColor(closestValue);

        chosenValueLbl.setText(String.format("#%06X", chosenValueRGB));
        closestValueLbl.setText(String.format("#%06X", closestValue));
        closestNameLbl.setText(closestName);

        chosenColourPad.setFill(chosenColour);
        closestColourPad.setFill(closestColor);

        chosenColourPad.setFill(chosenColour);
        closestColourPad.setFill(closestColor);
    }

    @FXML
    private void chooseColourRectangleClick(){
        valuePicker.show();
    }

    // TODO: hate this, gotta be a cleaner way
    //  and/or it should be done in the namer and under test.
    private Color toColor(int rgbInt){
        double scale = 1.0/255;
        return new Color(
                scale * (rgbInt >> 16 & 0xFF),
                scale * (rgbInt >> 8 & 0xFF),
                scale * (rgbInt & 0xFF),
                1
        );
    }
}
