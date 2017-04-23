package main.java.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import main.java.fxapp.Main;
import main.java.model.POI;
import main.java.model.Type;

import java.io.File;

/**
 * Created by Ashwin Ignatius on 4/22/2017.
 */
public class POIDetailController extends Controller {

    @FXML
    private ComboBox<Type> type;

    @FXML
    private TextField startData;

    @FXML
    private TextField endData;

    @FXML
    private DatePicker startDate;

    @FXML
    private Spinner startHour;

    @FXML
    private Spinner startMin;

    @FXML
    private DatePicker endDate;

    @FXML
    private Spinner endHour;

    @FXML
    private Spinner endMin;

    public String location;

    private ObservableList<Type> typeList = FXCollections.observableArrayList(Type.Mold, Type.Air_Quality);

    public void setLocation(String location) {
        this.location = location;
    }

    @FXML
    private void initialize() throws Exception{
        this.db = Main.getDb();
        type.setItems(typeList);
        System.out.println("Hello");
    }

    @FXML
    private void handleBackPressed() {myApp.load(new File("../view/ViewPOIScreen.fxml")); }
}
