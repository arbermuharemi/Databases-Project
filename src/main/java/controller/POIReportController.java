package main.java.controller;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
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
    private TableColumn<POI, Double> moldMinCol;
    private TableColumn<POI, Double> moldAvgCol;
    private TableColumn<POI, Double> moldMaxCol;
    private TableColumn<POI, Double> aqMinCol;
    private TableColumn<POI, Double> aqAvgCol;
    private TableColumn<POI, Double> aqMaxCol;
    private TableColumn<POI, Integer> numPointsCol;
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
                new Callback<TableColumn.CellDataFeatures<POI, Double>, ObservableValue<Double>>() {
                    @Override
                    public ObservableValue call(TableColumn.CellDataFeatures dataFeatures) {
                        POI poi = (POI) dataFeatures.getValue();
                        return new SimpleDoubleProperty(poi.getMoldMin());
                    }
                }
        );

        moldAvgCol.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<POI, Double>, ObservableValue<Double>>() {
                    @Override
                    public ObservableValue call(TableColumn.CellDataFeatures dataFeatures) {
                        POI poi = (POI) dataFeatures.getValue();
                        return new SimpleDoubleProperty(poi.getMoldAvg());
                    }
                }
        );

        moldMaxCol.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<POI, Double>, ObservableValue<Double>>() {
                    @Override
                    public ObservableValue call(TableColumn.CellDataFeatures dataFeatures) {
                        POI poi = (POI) dataFeatures.getValue();
                        return new SimpleDoubleProperty(poi.getMoldMax());
                    }
                }
        );

        aqMinCol.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<POI, Double>, ObservableValue<Double>>() {
                    @Override
                    public ObservableValue call(TableColumn.CellDataFeatures dataFeatures) {
                        POI poi = (POI) dataFeatures.getValue();
                        return new SimpleDoubleProperty(poi.getAqMin());
                    }
                }
        );

        aqAvgCol.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<POI, Double>, ObservableValue<Double>>() {
                    @Override
                    public ObservableValue call(TableColumn.CellDataFeatures dataFeatures) {
                        POI poi = (POI) dataFeatures.getValue();
                        return new SimpleDoubleProperty(poi.getAqAvg());
                    }
                }
        );

        aqMaxCol.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<POI, Double>, ObservableValue<Double>>() {
                    @Override
                    public ObservableValue call(TableColumn.CellDataFeatures dataFeatures) {
                        POI poi = (POI) dataFeatures.getValue();
                        return new SimpleDoubleProperty(poi.getAqMax());
                    }
                }
        );

        numPointsCol.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<POI, Integer>, ObservableValue<Integer>>() {
                    @Override
                    public ObservableValue call(TableColumn.CellDataFeatures dataFeatures) {
                        POI poi = (POI) dataFeatures.getValue();
                        return new SimpleIntegerProperty(poi.getNumPoints());
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
                "SELECT `LocationName`, \n"
                        + "       `City`, \n"
                        + "       `State`, \n"
                        + "       `Mold_Min`, \n"
                        + "       `Mold_Avg`, \n"
                        + "       `Mold_Max`, \n"
                        + "       `AQ_Min`, \n"
                        + "       `AQ_Avg`, \n"
                        + "       `AQ_Max`, \n"
                        + "       Ifnull(`Mold_Count`, 0) \n"
                        + "       + Ifnull(`AQ_Count`, 0) AS Count, \n"
                        + "       `Flag` \n"
                        + "FROM   ( `POI` \n"
                        + "         INNER JOIN (SELECT * \n"
                        + "                     FROM   ((SELECT `LocationName` AS Mold_Location, \n"
                        + "                                    Min(DataValue) AS Mold_Min, \n"
                        + "                                    Avg(DataValue) AS Mold_Avg, \n"
                        + "                                    Max(DataValue) AS Mold_Max, \n"
                        + "                                    Count(*)       AS Mold_Count \n"
                        + "                             FROM   `Data_Point` \n"
                        + "                             WHERE  `Type` = \"Mold\" \n"
                        + "                                    AND `Accepted` = \"1\" \n"
                        + "                             GROUP  BY `LocationName`) MoldTable \n"
                        + "                             LEFT OUTER JOIN (SELECT \n"
                        + "                             `LocationName` AS AQ_Location, \n"
                        + "                             Min(DataValue) AS AQ_Min, \n"
                        + "                             Avg(DataValue) AS AQ_Avg, \n"
                        + "                             Max(DataValue) AS AQ_Max, \n"
                        + "                             Count(*)       AS AQ_Count \n"
                        + "                                              FROM   `Data_Point` \n"
                        + "                                              WHERE  `Type` = \"Air_Quality\" \n"
                        + "                                                     AND `Accepted` = \"1\" \n"
                        + "                                              GROUP  BY `LocationName`) AQTable \n"
                        + "                                          ON MoldTable.Mold_Location = \n"
                        + "                                             AQTable.AQ_Location) \n"
                        + "                     UNION \n"
                        + "                     SELECT * \n"
                        + "                     FROM   ((SELECT `LocationName` AS Mold_Location, \n"
                        + "                                    Min(DataValue) AS Mold_Min, \n"
                        + "                                    Avg(DataValue) AS Mold_Avg, \n"
                        + "                                    Max(DataValue) AS Mold_Max, \n"
                        + "                                    Count(*)       AS Mold_Count \n"
                        + "                             FROM   `Data_Point` \n"
                        + "                             WHERE  `Type` = \"Mold\" \n"
                        + "                                    AND `Accepted` = \"1\" \n"
                        + "                             GROUP  BY `LocationName`) MoldTable \n"
                        + "                             RIGHT OUTER JOIN (SELECT \n"
                        + "                             `LocationName` AS AQ_Location, \n"
                        + "                             Min(DataValue) AS AQ_Min, \n"
                        + "                             Avg(DataValue) AS AQ_Avg, \n"
                        + "                             Max(DataValue) AS AQ_Max, \n"
                        + "                             Count(*)       AS AQ_Count \n"
                        + "                                               FROM   `Data_Point` \n"
                        + "                                               WHERE  `Type` = \"Air_Quality\" \n"
                        + "                                                      AND `Accepted` = \"1\" \n"
                        + "                                               GROUP  BY `LocationName`) AQTable \n"
                        + "                                           ON MoldTable.Mold_Location = \n"
                        + "                                              AQTable.AQ_Location))UnionTable \n"
                        + "                 ON UnionTable.Mold_Location = POI.LocationName \n"
                        + "                     OR UnionTable.AQ_Location = POI.LocationName) \n");
        db.rs.beforeFirst();
        while (db.rs.next()) {
            POI poi = new POI();
            poi.setLocationName(db.rs.getString("LocationName"));
            poi.setCity(db.rs.getString("City"));
            poi.setState(db.rs.getString("State"));
            poi.setMoldMin(db.rs.getDouble("Mold_Min"));
            poi.setMoldAvg(db.rs.getDouble("Mold_Avg"));
            poi.setMoldMax(db.rs.getDouble("Mold_Max"));
            poi.setAqMin(db.rs.getDouble("AQ_Min"));
            poi.setAqAvg(db.rs.getDouble("AQ_Avg"));
            poi.setAqMax(db.rs.getDouble("AQ_Max"));
            poi.setNumPoints(db.rs.getInt("Count"));
            poi.setFlagged(db.rs.getBoolean("Flag"));
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
