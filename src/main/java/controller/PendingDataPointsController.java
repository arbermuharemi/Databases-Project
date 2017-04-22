package main.java.controller;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.util.Callback;
import main.java.fxapp.Main;

import java.io.File;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

import main.java.model.DataPoint;
import main.java.model.Type;

/**
 * Created by Ashwin Ignatius on 4/21/2017.
 */
public class PendingDataPointsController extends Controller {

    @FXML
    private TableView table;

    private TableColumn checkBoxCol;

    private TableColumn locaionCol;

    private TableColumn typeCol;

    private TableColumn valueCol;

    private TableColumn dateCol;

    private ObservableList<DataPoint> data = FXCollections.observableArrayList();;

    public void initialize() throws SQLException {
        this.db = Main.getDb();
        checkBoxCol = new TableColumn("Select");
        locaionCol = new TableColumn("POI Location");
        typeCol = new TableColumn("Data Type");
        valueCol = new TableColumn("Data Value");
        dateCol = new TableColumn("Time & Date of Data Reading");

        checkBoxCol.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures, ObservableValue>() {
                    @Override
                    public ObservableValue call(TableColumn.CellDataFeatures dataFeatures) {
                        CheckBox checkBox = new CheckBox();
                        return new SimpleObjectProperty<CheckBox>(checkBox);
                    }
        });
        //checkBoxCol.setCellValueFactory( new Callback<TableColumn.CellDataFeatures<DataPoint, CheckBox>, ObservableValue<CheckBox>>() {
        //    @Override
        //    public ObservableValue<CheckBox> call(
        //            TableColumn.CellDataFeatures<DataPoint, CheckBox> data) {
        //        DataPoint dataPoint = data.getValue();
        //        CheckBox checkBox = new CheckBox();
        //        System.out.println(checkBox.selectedProperty());
        //        checkBox.selectedProperty().setValue(Boolean.TRUE);
        //        return new SimpleObjectProperty<CheckBox>(checkBox);
        //    }
        //});
        checkBoxCol.setStyle("-fx-alignment: CENTER;");
        checkBoxCol.setEditable(true);

        locaionCol.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures, ObservableValue>() {
                    @Override
                    public ObservableValue call(TableColumn.CellDataFeatures dataFeatures) {
                        DataPoint dataPoint = (DataPoint) dataFeatures.getValue();
                        return new SimpleStringProperty(dataPoint.getLocationName());
                    }
                }
        );

        typeCol.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures, ObservableValue>() {
                    @Override
                    public ObservableValue call(TableColumn.CellDataFeatures dataFeatures) {
                        DataPoint dataPoint = (DataPoint) dataFeatures.getValue();
                        return new SimpleStringProperty(dataPoint.getPointTypeString());
                    }
                }
        );

        valueCol.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures, ObservableValue>() {
                    @Override
                    public ObservableValue call(TableColumn.CellDataFeatures dataFeatures) {
                        DataPoint dataPoint = (DataPoint) dataFeatures.getValue();
                        return new SimpleStringProperty(dataPoint.getDataValue() + "");
                    }
                }
        );

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
        db.rs = db.stmt.executeQuery("SELECT * FROM `Data_Point`");
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

        //for (DataPoint d: data) {
        //    CheckBox cb = (CheckBox) checkBoxCol.getCellData(d);
        //    cb.selectedProperty().addListener(
        //            ((observable, oldValue, newValue) -> {
        //                d.setSelected(newValue.booleanValue());
        //            })
        //    );
        //}
        table.setItems(data);
        table.getColumns().addAll(checkBoxCol, locaionCol, typeCol, valueCol, dateCol);
    }

    @FXML
    public void handleBackPressed() {
        myApp.load(new File("../view/AdminHome.fxml"));
    }

    @FXML
    public void handleRejectPressed() {

    }

    @FXML
    public void handleAcceptPressed() {
        for (Object o : table.getItems()) {
            //DataPoint a = (DataPoint) o;
            //System.out.println(a.isSelected());
            Object a = checkBoxCol.getCellData(o);
            System.out.println(((CheckBox) (checkBoxCol.getCellData(o))).isSelected());
        }
    }
}
