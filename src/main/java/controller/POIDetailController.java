package main.java.controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.Callback;
import main.java.fxapp.Main;
import main.java.model.DataPoint;
import main.java.model.POI;
import main.java.model.Type;

import java.io.File;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

/**
 * Created by Ashwin Ignatius on 4/22/2017.
 */
public class POIDetailController extends Controller {

    @FXML
    private TableView<DataPoint> table;

    @FXML
    private ComboBox<String> type;

    @FXML
    private TextField startData;

    @FXML
    private TextField endData;

    @FXML
    private DatePicker startDate;

    @FXML
    private ComboBox<Integer> startHour;

    @FXML
    private ComboBox<Integer> startMin;

    @FXML
    private DatePicker endDate;

    @FXML
    private ComboBox<Integer> endHour;

    @FXML
    private ComboBox<Integer> endMin;

    @FXML
    private Button unflagButton;

    @FXML
    private Button flagButton;

    private ObservableList<Integer> hoursList = FXCollections.observableArrayList();

    private ObservableList<Integer> minutesList = FXCollections.observableArrayList();

    private TableColumn typeCol;

    private TableColumn valueCol;

    private TableColumn dateCol;

    private String location;

    private boolean isFlagged;

    private ObservableList<String> types = FXCollections.observableArrayList();

    private ObservableList<DataPoint> data = FXCollections.observableArrayList();

    public void setLocation(String location, boolean isFlagged) throws Exception {
        this.location = location;
        this.isFlagged = isFlagged;
        this.db = Main.getDb();
        typeCol = new TableColumn("Data Type");
        valueCol = new TableColumn("Data Value");
        dateCol = new TableColumn("Time & Date of Data Reading");

        db.rs = db.stmt.executeQuery("SELECT * FROM Data_Type");
        db.rs.beforeFirst();
        while (db.rs.next()) {
            types.add(db.rs.getString("Type"));
        }
        type.setItems(types);

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
                new Callback<TableColumn.CellDataFeatures, ObservableValue>() {
                    @Override
                    public ObservableValue call(TableColumn.CellDataFeatures dataFeatures) {
                        DataPoint dataPoint = (DataPoint) dataFeatures.getValue();
                        return new SimpleStringProperty(dataPoint.getDataValue() + "");
                    }
                }
        );
        valueCol.setSortable(true);

        dateCol.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures, ObservableValue>() {
                    @Override
                    public ObservableValue call(TableColumn.CellDataFeatures dataFeatures) {
                        DataPoint dataPoint = (DataPoint) dataFeatures.getValue();

                        String date = new SimpleDateFormat("MM/dd/yyyy " +
                                "HH:mm").format(dataPoint.getMyDate());

                        return new SimpleStringProperty(date);
                    }
                }
        );

        db.preparedStatement = db.conn.prepareStatement(
                "SELECT `Type`, `DataValue`, `DateTime` "
                        + "FROM `Data_Point` "
                        + "WHERE LocationName = ? "
                        + "AND Accepted = '1' ");
        db.preparedStatement.setString(1, location);
        db.rs = db.preparedStatement.executeQuery();
        db.rs.beforeFirst();
        data = FXCollections.observableArrayList();
        while (db.rs.next()) {
            DataPoint myPoint = new DataPoint();
            myPoint.setDataValue(db.rs.getInt("DataValue"));
            myPoint.setMyDate(db.rs.getTimestamp("DateTime"));
            myPoint.setPointType(Type.valueOf(db.rs.getString("Type")));
            data.add(myPoint);
        }
        if (isFlagged) {
            unflagButton.setDisable(false);
            flagButton.setDisable(true);
        } else {
            unflagButton.setDisable(true);
            flagButton.setDisable(false);
        }
        table.getColumns().addAll(typeCol, valueCol, dateCol);
        table.setItems(data);
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

    public void initialize() throws Exception {
        makeHoursList();
        makeMinutesList();
        startHour.setItems(hoursList);
        startMin.setItems(minutesList);
        endHour.setItems(hoursList);
        endMin.setItems(minutesList);
    }

    @FXML
    private void handleBackPressed() {myApp.load(new File("../view/ViewPOIScreen.fxml")); }

    @FXML
    private void handleFlagPressed() throws SQLException{
        db.preparedStatement = db.conn.prepareStatement(
                "UPDATE POI " +
                        "SET Flag = '1'" +
                        "WHERE LocationName = ?");
        db.preparedStatement.setString(1, location);
        db.preparedStatement.executeUpdate();
        flagButton.setDisable(true);
        unflagButton.setDisable(false);
    }

    @FXML
    private void handleUnFlagPressed() throws SQLException {
        db.preparedStatement = db.conn.prepareStatement(
                "UPDATE POI " +
                        "SET Flag = '0'" +
                        "WHERE LocationName = ?");
        db.preparedStatement.setString(1, location);
        db.preparedStatement.executeUpdate();
        unflagButton.setDisable(true);
        flagButton.setDisable(false);
    }


    @FXML
    private void handleApplyFilterPressed() throws SQLException {
        data.clear();
        String query = "SELECT `Type`, `DataValue`, `DateTime` "
                + "FROM `Data_Point` "
                + "WHERE Accepted = '1'"
                + "AND LocationName = '" + location + "'";
        String dataType = type.getValue();
        String beginData = startData.getText();
        String finData = endData.getText();
        LocalDate finDate = endDate.getValue();
        LocalDate beginDate = startDate.getValue();

        int beginHours;
        if (startHour.getValue() == null) {
            beginHours = 0;
        } else {
            beginHours = startHour.getValue();
        }
        int finHours;
        if (endHour.getValue() == null) {
            finHours = 0;
        } else {
            finHours = endHour.getValue();
        }
        int beginMin;
        if (startMin.getValue() == null) {
            beginMin = 0;
        } else {
            beginMin = startMin.getValue();
        }
        int finMin;
        if (endMin.getValue() == null) {
            finMin = 0;
        } else {
            finMin = endMin.getValue();
        }

        if (dataType != null) {
            query += "AND Type ='" + dataType + "'";
        }

        if (!beginData.isEmpty() && !finData.isEmpty()) {
            query += "AND DataValue BETWEEN'" + beginData + "'AND'" + finData + "'";
        } else if (!beginData.isEmpty() && finData.isEmpty()) {
            query += "AND DataValue >='" + beginData + "'";
        } else if (beginData.isEmpty() && !finData.isEmpty()) {
            query += "AND DataValue <='" + finData + "'";
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        if (beginDate != null && finDate != null) {
            Timestamp startTime = new Timestamp(beginDate.getYear() - 1900, beginDate.getMonthValue() - 1,
                    beginDate.getDayOfMonth(), beginHours, beginMin, 0, 0);
            Timestamp endTime = new Timestamp(finDate.getYear() - 1900, finDate.getMonthValue() - 1,
                    finDate.getDayOfMonth(), finHours, finMin, 0, 0);
            query += "AND DateTime BETWEEN '" + dateFormat.format(startTime) + "' AND '" + dateFormat.format(endTime) + "'";
        } else if (beginDate != null && finDate == null) {
            Timestamp startTime = new Timestamp(beginDate.getYear() - 1900, beginDate.getMonthValue() - 1,
                    beginDate.getDayOfMonth(), beginHours, beginMin, 0, 0);
            query += "AND DateTime >= '" + dateFormat.format(startTime) + "'";
        } else if (beginDate == null && finDate != null) {
            Timestamp endTime = new Timestamp(finDate.getYear() - 1900, finDate.getMonthValue() - 1,
                    finDate.getDayOfMonth(), finHours, finMin, 0, 0);
            query += "AND DateTime <= '" + dateFormat.format(endTime) + "'";
        }

        System.out.println(query);
        db.rs = db.stmt.executeQuery(query);
        db.rs.beforeFirst();
        data = FXCollections.observableArrayList();
        while (db.rs.next()) {
            DataPoint myPoint = new DataPoint();
            myPoint.setDataValue(db.rs.getInt("DataValue"));
            myPoint.setMyDate(db.rs.getTimestamp("DateTime"));
            myPoint.setPointType(Type.valueOf(db.rs.getString("Type")));
            data.add(myPoint);
        }

        table.setItems(data);
    }

    @FXML
    private void handleResetFilterPressed() throws Exception { myApp.loadPOIDetail(location, isFlagged); }
}
