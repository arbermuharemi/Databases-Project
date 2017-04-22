package main.java.controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.util.Callback;
import main.java.fxapp.Main;
import main.java.model.POI;

import javax.annotation.Generated;
import java.io.File;
import java.sql.SQLException;

/**
 * Created by Arber on 4/22/2017.
 */
public class POIReportController extends Controller{

    @FXML
    private TableView table;

    private TableColumn locationCol;
    private TableColumn cityCol;
    private TableColumn stateCol;
    private TableColumn moldMinCol;
    private TableColumn moldAvgCol;
    private TableColumn moldMaxCol;
    private TableColumn aqMinCol;
    private TableColumn aqAvgCol;
    private TableColumn aqMaxCol;
    private TableColumn numPointsCol;
    private TableColumn flaggedCol;

    private ObservableList<POI> data = FXCollections.observableArrayList();

    public void initialize() throws SQLException{
        this.db = Main.getDb();
        locationCol = new TableColumn("Location");
        cityCol = new TableColumn("City");
        stateCol = new TableColumn("State");
        moldMinCol = new TableColumn("Mold Min");
        moldAvgCol = new TableColumn("Mold Avg");
        moldMaxCol = new TableColumn("Mold Max");
        aqMinCol = new TableColumn("AQ Min");
        aqAvgCol = new TableColumn("AQ Avg");
        aqMaxCol = new TableColumn("AQ Max");
        numPointsCol = new TableColumn("# of data points");
        flaggedCol = new TableColumn("Flagged?");

        locationCol.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures, ObservableValue>() {
                    @Override
                    public ObservableValue call(TableColumn.CellDataFeatures dataFeatures) {
                        POI poi = (POI) dataFeatures.getValue();
                        return new SimpleStringProperty(poi.getLocationName());
                    }
                }
        );
        locationCol.setSortable(false);

        cityCol.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures, ObservableValue>() {
                    @Override
                    public ObservableValue call(TableColumn.CellDataFeatures dataFeatures) {
                        POI poi = (POI) dataFeatures.getValue();
                        return new SimpleStringProperty(poi.getCity());
                    }
                }
        );
        cityCol.setSortable(false);

        stateCol.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures, ObservableValue>() {
                    @Override
                    public ObservableValue call(TableColumn.CellDataFeatures dataFeatures) {
                        POI poi = (POI) dataFeatures.getValue();
                        return new SimpleStringProperty(poi.getState());
                    }
                }
        );
        stateCol.setSortable(false);

        moldMinCol.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures, ObservableValue>() {
                    @Override
                    public ObservableValue call(TableColumn.CellDataFeatures dataFeatures) {
                        POI poi = (POI) dataFeatures.getValue();
                        return new SimpleStringProperty(poi.getMoldMin() + "");
                    }
                }
        );

        moldAvgCol.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures, ObservableValue>() {
                    @Override
                    public ObservableValue call(TableColumn.CellDataFeatures dataFeatures) {
                        POI poi = (POI) dataFeatures.getValue();
                        return new SimpleStringProperty(poi.getMoldAvg() + "");
                    }
                }
        );

        moldMaxCol.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures, ObservableValue>() {
                    @Override
                    public ObservableValue call(TableColumn.CellDataFeatures dataFeatures) {
                        POI poi = (POI) dataFeatures.getValue();
                        return new SimpleStringProperty(poi.getMoldMax() + "");
                    }
                }
        );

        aqMinCol.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures, ObservableValue>() {
                    @Override
                    public ObservableValue call(TableColumn.CellDataFeatures dataFeatures) {
                        POI poi = (POI) dataFeatures.getValue();
                        return new SimpleStringProperty(poi.getAqMin() + "");
                    }
                }
        );

        aqAvgCol.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures, ObservableValue>() {
                    @Override
                    public ObservableValue call(TableColumn.CellDataFeatures dataFeatures) {
                        POI poi = (POI) dataFeatures.getValue();
                        return new SimpleStringProperty(poi.getAqAvg() + "");
                    }
                }
        );

        aqMaxCol.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures, ObservableValue>() {
                    @Override
                    public ObservableValue call(TableColumn.CellDataFeatures dataFeatures) {
                        POI poi = (POI) dataFeatures.getValue();
                        return new SimpleStringProperty(poi.getAqMax() + "");
                    }
                }
        );

        numPointsCol.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures, ObservableValue>() {
                    @Override
                    public ObservableValue call(TableColumn.CellDataFeatures dataFeatures) {
                        POI poi = (POI) dataFeatures.getValue();
                        return new SimpleStringProperty(poi.getNumPoints() + "");
                    }
                }
        );

        flaggedCol.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures, ObservableValue>() {
                    @Override
                    public ObservableValue call(TableColumn.CellDataFeatures dataFeatures) {
                        POI poi = (POI) dataFeatures.getValue();
                        return new SimpleStringProperty(poi.getFlaggedString());
                    }
                }
        );
        //db.rs = db.stmt.executeQuery("SELECT LocationName AS 'POI' FROM POI WHERE LocationName = 'GSU'");
        db.rs = db.stmt.executeQuery(
        "SELECT LocationName AS 'POI Location', "
                + "City, "
                + "State, "
                + "Mold_Min AS 'Mold Min', "
                + "Mold_Avg AS 'Mold Avg', "
                + "Mold_Max AS 'Mold Max', "
                + "AQ_Min AS 'AQ Min', "
                + "AQ_Avg AS 'AQ Avg', "
                + "AQ_Max AS 'AQ Max', "
                + "Mold_Count + AQ_Count AS '# of Data Points', "
                + "Flag AS 'Flagged?' "
                + "FROM "
                + "(`POI` NATURAL JOIN "
                + "(SELECT `LocationName` , "
                + "MIN( DataValue ) AS Mold_Min, "
                + "AVG( DataValue ) AS Mold_Avg, "
                + "MAX( DataValue ) AS Mold_Max, "
                + "COUNT( * ) AS Mold_Count "
                + "FROM `Data_Point` "
                + "WHERE `Type` = 'Mold' "
                + "GROUP BY `LocationName`)MoldTable "
                + "NATURAL JOIN "
                + "(SELECT `LocationName` , "
                + "MIN( DataValue ) AS AQ_Min, "
                + "AVG( DataValue ) AS AQ_Avg, "
                + "MAX( DataValue ) AS AQ_Max, "
                + "COUNT( * ) AS AQ_Count "
                + "FROM `Data_Point` "
                + "WHERE `Type` = 'Air_Quality' "
                + "GROUP BY `LocationName`)AQTable)");
        db.rs.beforeFirst();
        while (db.rs.next()) {
            POI poi = new POI();
            poi.setLocationName(db.rs.getString("POI Location"));
            poi.setCity(db.rs.getString("City"));
            poi.setState(db.rs.getString("State"));
            poi.setMoldMin(db.rs.getDouble("Mold Min"));
            poi.setMoldAvg(db.rs.getDouble("Mold Avg"));
            poi.setMoldMax(db.rs.getDouble("Mold Max"));
            poi.setAqMin(db.rs.getDouble("AQ Min"));
            poi.setAqAvg(db.rs.getDouble("AQ Avg"));
            poi.setAqMax(db.rs.getDouble("AQ Max"));
            poi.setNumPoints(db.rs.getInt("# of Data Points"));
            poi.setFlagged(db.rs.getBoolean("Flagged?"));
            data.add(poi);
        }
        table.setItems(data);
        table.getColumns().addAll(locationCol, cityCol, stateCol,
                moldMinCol, moldAvgCol, moldMaxCol,
                aqMinCol, aqAvgCol, aqMaxCol,
                numPointsCol, flaggedCol);
    }

    @FXML
    public void handleBackPressed() {
        myApp.load(new File("../view/CityOfficialHome.fxml"));
    }

}
