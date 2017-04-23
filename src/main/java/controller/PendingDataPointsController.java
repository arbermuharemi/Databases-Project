package main.java.controller;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.Callback;
import main.java.fxapp.Main;

import java.io.File;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import main.java.model.DataPoint;
import main.java.model.Type;

import javax.xml.crypto.Data;

/**
 * Created by Ashwin Ignatius on 4/21/2017.
 */
public class PendingDataPointsController extends Controller {

    @FXML
    private TableView<DataPoint> table;

    private TableColumn locationCol;

    private TableColumn typeCol;

    private TableColumn<DataPoint, Double> valueCol;

    private TableColumn<DataPoint, Timestamp> dateCol;

    private ObservableList<DataPoint> data = FXCollections.observableArrayList();

    public void initialize() throws SQLException {
        this.db = Main.getDb();
        table.getSelectionModel().setSelectionMode(
                SelectionMode.MULTIPLE
        );
        locationCol = new TableColumn("POI Location");
        typeCol = new TableColumn("Data Type");
        valueCol = new TableColumn("Data Value");
        dateCol = new TableColumn("Time & Date of Data Reading");

        locationCol.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures, ObservableValue>() {
                    @Override
                    public ObservableValue call(TableColumn.CellDataFeatures dataFeatures) {
                        DataPoint dataPoint = (DataPoint) dataFeatures.getValue();
                        return new SimpleStringProperty(dataPoint.getLocationName());
                    }
                }
        );
        locationCol.setSortable(true);

        typeCol.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures, ObservableValue>() {
                    @Override
                    public ObservableValue call(TableColumn.CellDataFeatures dataFeatures) {
                        DataPoint dataPoint = (DataPoint) dataFeatures.getValue();
                        return new SimpleStringProperty(dataPoint.getPointTypeString());
                    }
                }
        );
        typeCol.setSortable(true);

        valueCol.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<DataPoint, Double>, ObservableValue<Double>>() {
                    @Override
                    public ObservableValue call(TableColumn.CellDataFeatures dataFeatures) {
                        DataPoint dataPoint = (DataPoint) dataFeatures.getValue();
                        return new SimpleDoubleProperty(dataPoint.getDataValue());
                    }
                }
        );
        valueCol.setSortable(true);

        dateCol.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<DataPoint, Timestamp>, ObservableValue<Timestamp>>() {
                    @Override
                    public ObservableValue call(TableColumn.CellDataFeatures dataFeatures) {
                        DataPoint dataPoint = (DataPoint) dataFeatures.getValue();

                        String date = new SimpleDateFormat("MM/dd/yyyy " +
                                "HH:mm").format(dataPoint.getMyDate());

                        return new SimpleObjectProperty(dataPoint.getMyDate());
                    }
                }
        );
        dateCol.setSortable(true);

        db.rs = db.stmt.executeQuery(
                "SELECT * FROM `Data_Point` "
                + "WHERE Accepted IS NULL ");
        db.rs.beforeFirst();
        data = FXCollections.observableArrayList();
        while (db.rs.next()) {
            DataPoint myPoint = new DataPoint();
            myPoint.setAccepted(db.rs.getBoolean("Accepted"));
            myPoint.setDataValue(db.rs.getInt("DataValue"));
            myPoint.setMyDate(db.rs.getTimestamp("DateTime"));
            myPoint.setPointType(Type.valueOf(db.rs.getString("Type")));
            myPoint.setLocationName(db.rs.getString("LocationName"));
            data.add(myPoint);
        }

        table.setItems(data);
        table.getColumns().addAll(locationCol, typeCol, valueCol, dateCol);
    }

    @FXML
    public void handleBackPressed() {
        myApp.load(new File("../view/AdminHome.fxml"));
    }

    @FXML
    public void handleRejectPressed() throws SQLException{
        for (DataPoint dataPoint : table.getSelectionModel().getSelectedItems()) {
            db.preparedStatement = db.conn.prepareStatement(
                    "UPDATE Data_Point " +
                            "SET Accepted = '0'" +
                            "WHERE LocationName = ?" +
                            "AND DateTime = ?");
            db.preparedStatement.setString(1, dataPoint.getLocationName());
            db.preparedStatement.setTimestamp(2, dataPoint.getMyDate());
            db.preparedStatement.executeUpdate();
        }
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Successfully Rejected");
        alert.setContentText("The data points you selected have been rejected.");
        alert.showAndWait();
        myApp.load(new File("../view/PendingDataPoints.fxml"));
    }

    @FXML
    public void handleAcceptPressed() throws SQLException{
        for (DataPoint dataPoint : table.getSelectionModel().getSelectedItems()) {
            db.preparedStatement = db.conn.prepareStatement(
                    "UPDATE Data_Point " +
                            "SET Accepted = '1'" +
                            "WHERE LocationName = ?" +
                            "AND DateTime = ?");
            db.preparedStatement.setString(1, dataPoint.getLocationName());
            db.preparedStatement.setTimestamp(2, dataPoint.getMyDate());
            db.preparedStatement.executeUpdate();
        }
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Successfully Accepted");
        alert.setContentText("The data points you selected have been accepted");
        alert.showAndWait();
        myApp.load(new File("../view/PendingDataPoints.fxml"));
    }
}
