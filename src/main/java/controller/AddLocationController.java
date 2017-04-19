package main.java.controller;

/**
 * Created by Ashwin Ignatius on 4/18/2017.
 */

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import main.java.fxapp.Main;
import main.java.model.DatabaseRef;
import main.java.model.UserType;
import java.io.File;

public class AddLocationController extends Controller {

    @FXML
    private TextField locName;

    @FXML
    private ComboBox city;

    @FXML
    private ComboBox state;

    @FXML
    private TextField zipcode;

    @FXML
    public void handleBackClicked() {
        myApp.load(new File("../view/AddDataPoint.fxml"));
    }
}
