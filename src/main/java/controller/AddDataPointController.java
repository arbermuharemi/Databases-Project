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
    public void handleBackPressed() {
        myApp.load(new File("../view/AddLocation.fxml"));
    }

    @FXML
    public void handleHyperlinkClicked() {
        myApp.load(new File("../view/AddLocation.fxml"));
    }
}
