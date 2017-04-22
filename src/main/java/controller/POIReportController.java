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
        "select * from ( \n" +
                "`POI` Left Outer Join (\n" +
                "select * from\n" +
                "((\n" +
                "SELECT `LocationName` as Mold_Location , MIN( DataValue ) AS Mold_Min, AVG( DataValue ) AS Mold_Avg, MAX( DataValue ) AS Mold_Max, COUNT( * ) AS Mold_Count\n" +
                "FROM `Data_Point` \n" +
                "WHERE `Type` = \"Mold\"\n" +
                "AND `Accepted` = \"1\"\n" +
                "GROUP BY `LocationName`\n" +
                ") MoldTable\n" +
                "LEFT OUTER JOIN (\n" +
                "SELECT `LocationName` as AQ_Location , MIN( DataValue ) AS AQ_Min, AVG( DataValue ) AS AQ_Avg, MAX( DataValue ) AS AQ_Max, COUNT( * ) AS AQ_Count\n" +
                "FROM `Data_Point` \n" +
                "WHERE `Type` = \"Air_Quality\"\n" +
                "AND `Accepted` = \"1\"\n" +
                "GROUP BY `LocationName`\n" +
                ") AQTable ON MoldTable.Mold_Location = AQTable.AQ_Location)\n" +
                "\n" +
                "UNION\n" +
                "\n" +
                "select * from\n" +
                "((\n" +
                "SELECT `LocationName` as Mold_Location , MIN( DataValue ) AS Mold_Min, AVG( DataValue ) AS Mold_Avg, MAX( DataValue ) AS Mold_Max, COUNT( * ) AS Mold_Count\n" +
                "FROM `Data_Point` \n" +
                "WHERE `Type` = \"Mold\"\n" +
                "AND `Accepted` = \"1\"\n" +
                "GROUP BY `LocationName`\n" +
                ") MoldTable\n" +
                "RIGHT OUTER JOIN (\n" +
                "SELECT `LocationName` as AQ_Location , MIN( DataValue ) AS AQ_Min, AVG( DataValue ) AS AQ_Avg, MAX( DataValue ) AS AQ_Max, COUNT( * ) AS AQ_Count\n" +
                "FROM `Data_Point` \n" +
                "WHERE `Type` = \"Air_Quality\"\n" +
                "AND `Accepted` = \"1\"\n" +
                "GROUP BY `LocationName`\n" +
                ") AQTable ON MoldTable.Mold_Location = AQTable.AQ_Location)\n" +
                ")UnionTable on UnionTable.Mold_Location = POI.LocationName OR UnionTable.AQ_Location = POI.LocationName)\n");
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
            Integer mold_count = db.rs.getInt("Mold_Count");
            Integer aq_count = db.rs.getInt("AQ_Count");
            Integer myPoints = 0;
            if (mold_count == null) {
                myPoints = aq_count;
            } else if (aq_count == null) {
                myPoints = mold_count;
            } else {
                myPoints = mold_count + aq_count;
            }
            poi.setNumPoints(myPoints);
            poi.setFlagged(db.rs.getBoolean("DateFlagged"));
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
