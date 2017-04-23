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
import java.sql.SQLException;

public class AddLocationController extends Controller {

    @FXML
    private TextField locName;

    @FXML
    private ComboBox<String> stateBox;

    @FXML
    private ComboBox<String> cityBox;

    @FXML
    private TextField zipcode;

    private ObservableList<String> stateList = FXCollections.observableArrayList();
    private ObservableList<String> cityList = FXCollections.observableArrayList();

    public void initialize() throws Exception {
        this.db = Main.getDb();
        db.rs = db.stmt.executeQuery(
                "SELECT DISTINCT State "
                        + "FROM `City_State`"
                        + "ORDER BY State ");
        db.rs.beforeFirst();
        while (db.rs.next()) {
            stateList.add(db.rs.getString("State"));
        }
        db.rs.beforeFirst();
        db.rs = db.stmt.executeQuery(
                "SELECT City "
                        + "FROM `City_State`"
                        + "ORDER BY City ");
        while (db.rs.next()) {
            cityList.add(db.rs.getString("City"));
        }
        stateBox.setItems(stateList);
    }

    @FXML
    private void changeCities() throws Exception {
        cityList.clear();
        db.preparedStatement = db.conn.prepareStatement(
                "SELECT City "
                        + "FROM City_State "
                        + "WHERE State = ?"
                        + "ORDER BY City");
        db.preparedStatement.setString(1, stateBox.getValue());
        //System.out.println(db.preparedStatement);
        try {
            db.rs = db.preparedStatement.executeQuery();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("An Error Occured!");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
            return;
        }
        db.rs.beforeFirst();
        while (db.rs.next()) {
            cityList.add(db.rs.getString("City"));
        }
        cityBox.setItems(cityList);
    }

    @FXML
    public void handleSubmitClicked() throws SQLException {
        if (locName.getText().isEmpty() || stateBox.getValue() == null
                || cityBox.getValue() == null || zipcode.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Missing Location Information");
            alert.setContentText("One or more fields are blank or have "
                    + "incorrectly formatted data. Please properly fill in "
                    + "the location name, state, city, zip code value before "
                    + "continuing.");
            alert.showAndWait();
            return;
        }
        try {
            Integer.parseInt(zipcode.getText());
            if (zipcode.getText().length() != 5) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Invalid Number Input");
            alert.setContentText("Please enter a valid 5 digit zip code that "
                    + "only includes numbers");
            alert.showAndWait();
            zipcode.clear();
            return;
        }
        String location = locName.getText();
        String state = stateBox.getValue();
        String city = cityBox.getValue();
        String zipCode = zipcode.getText();
        db.preparedStatement = db.conn.prepareStatement(
                "SELECT * "
                        + "FROM `POI` "
                        + "WHERE LocationName = ? ");
        db.preparedStatement.setString(1, location);
        //System.out.println(db.preparedStatement);
        try {
            db.rs = db.preparedStatement.executeQuery();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("An Error Occured!");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
            return;
        }
        if (db.rs.first()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("POI Location Already Exists");
            alert.setContentText("A POI location with this name already exists. "
                    + "Please ensure that you have entered the location name "
                    + "correctly and try again.");
            alert.showAndWait();
            return;
        }
        db.preparedStatement = db.conn.prepareStatement(
                "INSERT INTO POI"
                        + "(`LocationName` ,"
                        + "`Flag` ,"
                        + "`DateFlagged` ,"
                        + "`Zipcode` ,"
                        + "`City` ,"
                        + "`State`"
                        + ")"
                        + "VALUES ("
                        + "?, '0', NULL, ?, ?, ?"
                        + ")"
        );
        db.preparedStatement.setString(1, location);
        db.preparedStatement.setString(2, zipCode);
        db.preparedStatement.setString(3, city);
        db.preparedStatement.setString(4, state);
        try {
            db.preparedStatement.executeUpdate();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("An Error Occured!");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
            return;
        }
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Successfully Added");
        alert.setContentText("Your location was successfully added!");
        alert.showAndWait();
        myApp.load(new File("../view/AddDataPoint.fxml"));
    }

    @FXML
    public void handleBackClicked() {
        myApp.load(new File("../view/AddDataPoint.fxml"));
    }
}
