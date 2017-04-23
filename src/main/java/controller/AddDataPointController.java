package main.java.controller;

/**
 * Created by Ashwin Ignatius on 4/17/2017.
 */

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import main.java.fxapp.Main;
import java.io.File;
import java.time.LocalDate;
import java.sql.Timestamp;

public class AddDataPointController extends Controller {

    @FXML
    private ComboBox<String> locName;

    @FXML
    private DatePicker dateTime;

    @FXML
    private ComboBox<String> dataType;

    @FXML
    private TextField dataValue;

    @FXML
    private ComboBox<Integer> hours;

    @FXML
    private ComboBox<Integer> minutes;

    private ObservableList<String> types = FXCollections.observableArrayList();

    private ObservableList<String> pois = FXCollections.observableArrayList();

    private ObservableList<Integer> hoursList = FXCollections.observableArrayList();

    private ObservableList<Integer> minutesList = FXCollections.observableArrayList();

    @FXML
    private void initialize() throws Exception {
        this.db = Main.getDb();
        db.rs = db.stmt.executeQuery(
                "SELECT LocationName "
                        + "FROM POI "
                        + "ORDER BY LocationName");
        db.rs.beforeFirst();
        while (db.rs.next()) {
            pois.add(db.rs.getString("LocationName"));
        }
        locName.setItems(pois);
        db.rs = db.stmt.executeQuery("SELECT * FROM Data_Type");
        db.rs.beforeFirst();
        while (db.rs.next()) {
            types.add(db.rs.getString("Type"));
        }
        dataType.setItems(types);
        makeHoursList();
        makeMinutesList();
        hours.setItems(hoursList);
        minutes.setItems(minutesList);
    }

    private void makeHoursList() {
        for (int i = 0; i < 24; i++) {
            hoursList.add(i);
        }
    }

    private void makeMinutesList() {
        for (int i = 0; i < 60; i++) {
            minutesList.add(i);
        }
    }

    @FXML
    public void handleSubmitPressed() throws Exception {
        if (dateTime.getValue() == null || hours.getValue() == null
                || minutes.getValue() == null || dataValue.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Missing Data Point Information");
            alert.setContentText("One or more fields are blank or have "
                    + "incorrectly formatted data. Please properly fill in "
                    + "the date, time, and data value before "
                    + "continuing.");
            alert.showAndWait();
            return;
        }
        try {
            Integer.parseInt(dataValue.getText());
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Invalid Number Input");
            alert.setContentText("Please enter a valid integer (no decimals) "
                    + "into the Data Value field");
            alert.showAndWait();
            dataValue.clear();
            return;
        }
        String dataLocName = locName.getValue();
        LocalDate date = dateTime.getValue();
        if (date.isAfter(LocalDate.now())) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Invalid Date Input");
            alert.setContentText("The date you've entered is in the future"
                    + ", Mcfly! Please enter a valid date");
            alert.showAndWait();
            return;
        }
        int hour = hours.getValue();
        int minute = minutes.getValue();
        Timestamp dataDate = new Timestamp(date.getYear() - 1900, date.getMonthValue() - 1,
                date.getDayOfMonth(), hour, minute, 0, 0);
        String databaseType = dataType.getValue();
        int value = Integer.parseInt(dataValue.getText());
        db.preparedStatement = db.conn.prepareStatement(
                "INSERT INTO `Data_Point` "
                        + "(`LocationName` ,"
                        + "`DateTime` ,"
                        + "`Accepted` ,"
                        + "`DataValue` ,"
                        + "`Type`) "
                        + "VALUES "
                        + "("
                        + "?, ?, NULL, ?, ?"
                        + ")"
        );
        db.preparedStatement.setString(1, dataLocName);
        db.preparedStatement.setTimestamp(2, dataDate);
        db.preparedStatement.setInt(3, value);
        db.preparedStatement.setString(4, databaseType);
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
        alert.setTitle("Successfully Added!");
        alert.setContentText("Your data point has been added is awaiting "
                + "approval.");
        alert.showAndWait();
        myApp.load(new File("../view/AddDataPoint.fxml"));
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
