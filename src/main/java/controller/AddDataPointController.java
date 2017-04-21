package main.java.controller;

/**
 * Created by Ashwin Ignatius on 4/17/2017.
 */

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import main.java.fxapp.Main;
import main.java.model.DatabaseRef;
import main.java.model.UserType;
import java.io.File;
import java.util.ArrayList;

public class AddDataPointController extends Controller {

    @FXML
    private ComboBox locName;

    @FXML
    private DatePicker dateTime;

    @FXML
    private ComboBox dataType;

    @FXML
    private TextField dataValue;

    @FXML
    private ComboBox Hours;

    @FXML
    private ComboBox Minutes;

    private ObservableList<String> hoursList = FXCollections.observableArrayList();

    private ObservableList<String> minutesList = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        makeHoursList();
        makeminutesList();
        Hours.setItems(hoursList);
        Minutes.setItems(minutesList);
    }

    private void makeHoursList() {
        for (int i = 0; i < 24; i++) {
            hoursList.add(String.format("%02d", i));
        }
    }

    private void makeminutesList() {
        for (int i = 0; i < 60; i++) {
            minutesList.add(String.format("%02d", i));
        }
    }

    @FXML
    public void handleBackPressed() {
        myApp.load(new File("../view/AddLocation.fxml"));
    }

    @FXML
    public void handleHyperlinkClicked() {
        myApp.load(new File("../view/AddLocation.fxml"));
    }

    @FXML
    public void handleLogoutPressed()  {
        myApp.load(new File("../view/LoginScreen.fxml"));
    }
}
