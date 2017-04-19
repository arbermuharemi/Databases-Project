package main.java.controller;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;

import java.io.File;

/**
 * Created by Arber on 4/19/2017.
 * This class's purpose is to: <DESCRIBE PURPOSE>
 */
public class PendingCityOfficialsController extends Controller {

    @FXML
    private TableView table;

    @FXML
    public void handleBackPressed() {
        myApp.load(new File("../view/AdminHome.fxml"));
    }

    @FXML
    public void handleRejectPressed() {

    }

    @FXML
    public void handleAcceptPressed() {

    }

}
