package main.java.controller;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Callback;
import main.java.fxapp.Main;
import main.java.model.DataPoint;
import main.java.model.DatabaseRef;
import main.java.model.POI;
import main.java.model.Type;

import java.io.File;
import java.sql.SQLException;
import java.text.SimpleDateFormat;


public class ViewPOIController extends Controller{

    @FXML
    private TableView table;

    @FXML
    private ComboBox<String> locName;

    @FXML
    private DatePicker dateTimeStart;

    @FXML
    private ComboBox<String> dataType;

    @FXML
    private TextField zipCode;

    @FXML
    private DatePicker dateTimeEnd;

    @FXML
    private CheckBox flagged;

    @FXML
    private ComboBox<String> city;

    @FXML
    private ComboBox<String> state;


    private ObservableList<String> pois = FXCollections.observableArrayList();

    private ObservableList<String> cities = FXCollections.observableArrayList();

    private ObservableList<String> states = FXCollections.observableArrayList();

    private ObservableList<String> types = FXCollections.observableArrayList();


    @FXML
    //Location Name, City, State, Zip Code, Flagged, Date Flagged
    //locationCol, cityCol, stateCol, zipCodeCol, checkBoxCol, dateCol

    private TableColumn locationCol;

    private TableColumn cityCol;

    private TableColumn stateCol;

    private TableColumn zipCodeCol;

    private TableColumn checkBoxCol;

    private TableColumn dateCol;

    private ObservableList<POI> data = FXCollections.observableArrayList();

    @FXML
    private void initialize() throws Exception {
        this.db = Main.getDb();

        db.rs = db.stmt.executeQuery("SELECT LocationName, City, State FROM POI");
        db.rs.beforeFirst();
        while (db.rs.next()) {
            pois.add(db.rs.getString("LocationName"));
            String myState = db.rs.getString("State");
            String myCity = db.rs.getString("City");
            if (!states.contains(myState)) {
                states.add(db.rs.getString("State"));
            }
            if (!cities.contains(myCity)) {
                cities.add(db.rs.getString("City"));
            }
        }
        locName.setItems(pois);
        city.setItems(cities);
        state.setItems(states);


        db.rs = db.stmt.executeQuery("SELECT * FROM Data_Type");
        db.rs.beforeFirst();
        while (db.rs.next()) {
            types.add(db.rs.getString("Type"));
        }
        dataType.setItems(types);

        //Location Name, City, State, Zip Code, Flagged, Date Flagged
        //locationCol, cityCol, stateCol, zipCodeCol, checkBoxCol, dateCol

        locationCol = new TableColumn("Location Name");
        cityCol = new TableColumn("City");
        stateCol = new TableColumn("State");
        zipCodeCol = new TableColumn("Zip Code");
        checkBoxCol = new TableColumn("Flagged?");
        dateCol = new TableColumn("Date Flagged");


        locationCol.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures, ObservableValue>() {
                    @Override
                    public ObservableValue call(TableColumn.CellDataFeatures dataFeatures) {
                        POI poi = (POI) dataFeatures.getValue();
                        return new SimpleStringProperty(poi.getLocationName());
                    }
                }
        );


        cityCol.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures, ObservableValue>() {
                    @Override
                    public ObservableValue call(TableColumn.CellDataFeatures dataFeatures) {
                        POI poi = (POI) dataFeatures.getValue();
                        return new SimpleStringProperty(poi.getCity());
                    }
                }
        );


        stateCol.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures, ObservableValue>() {
                    @Override
                    public ObservableValue call(TableColumn.CellDataFeatures dataFeatures) {
                        POI poi = (POI) dataFeatures.getValue();
                        return new SimpleStringProperty(poi.getState());
                    }
                }
        );


        zipCodeCol.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures, ObservableValue>() {
                    @Override
                    public ObservableValue call(TableColumn.CellDataFeatures dataFeatures) {
                        POI poi = (POI) dataFeatures.getValue();
                        return new SimpleStringProperty(poi.getZip());
                    }
                }
        );


        dateCol.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures, ObservableValue>() {
                    @Override
                    public ObservableValue call(TableColumn.CellDataFeatures dataFeatures) {
                        POI poi = (POI) dataFeatures.getValue();
                        return new SimpleStringProperty(poi.getDateFlagged() + "");
                    }
                }
        );

        //locationCol, cityCol, stateCol, zipCodeCol, dateCol

        db.rs = db.stmt.executeQuery("SELECT * FROM `POI` ");
        db.rs.beforeFirst();
        data = FXCollections.observableArrayList();
        while (db.rs.next()) {
            POI poi = new POI();

            poi.setLocationName(db.rs.getString("LocationName"));
            poi.setCity(db.rs.getString("City"));
            poi.setState(db.rs.getString("State"));
            poi.setZip(db.rs.getString("Zipcode"));
            poi.setDateFlagged(db.rs.getTimestamp("DateFlagged"));
            data.add(poi);
        }


        table.setItems(data);
        table.getColumns().addAll(locationCol, cityCol, stateCol, zipCodeCol, dateCol);

    }


    @FXML
    public void handleApplyFilterPressed() {

    }


    @FXML
    public void handleResetFilterPressed() {
        myApp.load(new File("../view/ViewPOIScreen.fxml"));
    }

    @FXML
    public void handleBackPressed()  {
        myApp.load(new File("../view/CityOfficialHome.fxml"));
    }


}
