package main.java.controller;

import javafx.beans.property.SimpleBooleanProperty;
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
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;


public class ViewPOIController extends Controller{

    @FXML
    private TableView<POI> table;

    @FXML
    private ComboBox<String> locName;

    @FXML
    private DatePicker dateTimeStart;

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
        /*table.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                System.out.println(newSelection.getLocationName());
                myApp.loadPOIDetail(new File("../view/POIDetail.fxml"), newSelection.getLocationName());
            }
        });*/

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

        checkBoxCol.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures, ObservableValue>() {
                    @Override
                    public ObservableValue call(TableColumn.CellDataFeatures dataFeatures) {
                        POI poi = (POI) dataFeatures.getValue();
                        return new SimpleBooleanProperty(poi.getFlagged());
                    }
                }
        );


        dateCol.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures, ObservableValue>() {
                    @Override
                    public ObservableValue call(TableColumn.CellDataFeatures dataFeatures) {
                        POI poi = (POI) dataFeatures.getValue();
                        //String date = new SimpleDateFormat("MM/dd/yyyy " +
                                //"HH:mm").format(poi.getDateFlagged());
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
            poi.setFlagged(db.rs.getBoolean("Flag"));
            poi.setDateFlagged(db.rs.getTimestamp("DateFlagged"));
            data.add(poi);
        }


        table.setItems(data);
        table.getColumns().addAll(locationCol, cityCol, stateCol, zipCodeCol, checkBoxCol, dateCol);

    }

    public boolean baseChecker(String base) {
        if (base.equals("SELECT * FROM POI ")) {
            return true;
        }
        return false;
    }

    @FXML
    public void handleApplyFilterPressed() throws SQLException {
        data.clear();
        String query = "SELECT * FROM POI ";
        String locat = locName.getValue();
        String cityData = city.getValue();
        String stateData = state.getValue();
        Boolean isFlagged = flagged.isSelected();
        LocalDate start = dateTimeStart.getValue();
        LocalDate end = dateTimeEnd.getValue();
        if (locat != null) {
            query += "WHERE LocationName ='" + locat + "'";
        }
        if (cityData != null) {
            if (baseChecker(query)) {
                query += "WHERE ";
            } else {
                query += "AND ";
            }
             query += "City ='" + cityData + "'";
        }
        if (stateData != null) {
            if (baseChecker(query)) {
                query += "WHERE ";
            } else {
                query += "AND ";
            }
            query += "State ='" + stateData + "'";
        }
        if (isFlagged) {
            if (baseChecker(query)) {
                query += "WHERE ";
            } else {
                query += "AND ";
            }
            query += "Flag =" + isFlagged;
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        if (start != null && end != null) {
            Timestamp startTime = new Timestamp(start.getYear() - 1900, start.getMonthValue() - 1,
                    start.getDayOfMonth(), 0, 0, 0, 0);
            Timestamp endTime = new Timestamp(end.getYear() - 1900, end.getMonthValue() - 1,
                    end.getDayOfMonth(), 0, 0, 0, 0);
            if (baseChecker(query)) {
                query += "WHERE ";
            } else {
                query += "AND ";
            }
            query += "DateFlagged BETWEEN '" + dateFormat.format(startTime) + "' AND '" + dateFormat.format(endTime) + "'";
        } else if (start != null && end == null) {
            Timestamp startTime = new Timestamp(start.getYear() - 1900, start.getMonthValue() - 1,
                    start.getDayOfMonth(), 0, 0, 0, 0);
            if (baseChecker(query)) {
                query += "WHERE ";
            } else {
                query += "AND ";
            }
            query += "DateFlagged >= '" + dateFormat.format(startTime) + "'";
        } else if (start == null && end != null) {
            Timestamp endTime = new Timestamp(end.getYear() - 1900, end.getMonthValue() - 1,
                    end.getDayOfMonth(), 0, 0, 0, 0);
            if (baseChecker(query)) {
                query += "WHERE ";
            } else {
                query += "AND ";
            }
            query += "DateFlagged <= '" + dateFormat.format(endTime) + "'";
        }

        System.out.println(query);
        db.rs = db.stmt.executeQuery(query);
        db.rs.beforeFirst();
        data = FXCollections.observableArrayList();
        while (db.rs.next()) {
            POI poi = new POI();
            poi.setLocationName(db.rs.getString("LocationName"));
            poi.setCity(db.rs.getString("City"));
            poi.setState(db.rs.getString("State"));
            poi.setZip(db.rs.getString("Zipcode"));
            poi.setFlagged(db.rs.getBoolean("Flag"));
            poi.setDateFlagged(db.rs.getTimestamp("DateFlagged"));
            data.add(poi);
        }

        table.setItems(data);
    }


    @FXML
    public void handleResetFilterPressed() {
        myApp.load(new File("../view/ViewPOIScreen.fxml"));
    }

    @FXML
    public void handleBackPressed()  {
        myApp.load(new File("../view/CityOfficialHome.fxml"));
    }

    @FXML
    public void handleViewDetailPressed() { myApp.loadPOIDetail(table.getSelectionModel().getSelectedItem().getLocationName().toString()); }
}
