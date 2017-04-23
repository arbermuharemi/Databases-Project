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
import java.text.SimpleDateFormat;

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
    private Spinner startHour;

    @FXML
    private Spinner startMin;

    @FXML
    private DatePicker endDate;

    @FXML
    private Spinner endHour;

    @FXML
    private Spinner endMin;

    private TableColumn typeCol;

    private TableColumn valueCol;

    private TableColumn dateCol;

    private String location;

    private ObservableList<String> types = FXCollections.observableArrayList();

    private ObservableList<DataPoint> data = FXCollections.observableArrayList();

    public void setLocation(String location) throws Exception {
        this.location = location;
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

        table.getColumns().addAll(typeCol, valueCol, dateCol);
        table.setItems(data);
    }


    public void initialize() throws Exception {

    }



    @FXML
    private void handleBackPressed() {myApp.load(new File("../view/ViewPOIScreen.fxml")); }
}
