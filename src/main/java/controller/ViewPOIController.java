package main.java.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import main.java.fxapp.Main;
import main.java.model.DatabaseRef;

import java.io.File;
import java.sql.SQLException;
/**
 * Created by Arber on 4/21/2017.
 */
public class ViewPOIController extends Controller{

    @FXML
    private ComboBox<String> locName;

    @FXML
    private ComboBox<String> cityName;

    @FXML
    private ComboBox<String> stateName;

    @FXML
    private ComboBox<String> dataType;

    @FXML
    private TextField zipcode;

    @FXML
    private CheckBox flagged;

    @FXML
    private DatePicker dateTime;

    private ObservableList<String> locations = FXCollections.observableArrayList();

    @FXML
    private void initialize() throws Exception {
        this.db = Main.getDb();
        db.rs = db.stmt.executeQuery("SELECT LocationName FROM POI");
        while(db.rs.next()) {
            locations.add(db.rs.getString("LocationName"));

        }
        locName.setItems(locations);
    }

    @FXML
    public void handleBackPressed()  {
        myApp.load(new File("../view/CityOfficialHome.fxml"));
    }


}
