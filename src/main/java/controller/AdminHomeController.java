package main.java.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Hyperlink;
import main.java.fxapp.Main;
import main.java.model.DatabaseRef;

import java.io.File;

/**
 * Created by Arber on 4/10/2017.
 * This class's purpose is to: <DESCRIBE PURPOSE>
 */
public class AdminHomeController extends Controller {

    @FXML
    private void handleLogOutPressed() {
        myApp.load(new File("../view/LoginScreen.fxml"));
    }

    @FXML
    public void handlePendingCityOfficialPressed() {
        myApp.load(new File("../view/PendingCityOfficialScreen.fxml"));
    }

    @FXML
    public void handlePendingDataPointsPressed()  {
        myApp.load(new File("../view/PendingDataPoints.fxml"));
    }
}
