package main.java.controller;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import main.java.fxapp.Main;
import main.java.model.DatabaseRef;

import java.io.File;
import java.sql.SQLException;
import main.java.model.DataPoint;
import main.java.model.Type;

/**
 * Created by Ashwin Ignatius on 4/21/2017.
 */
public class PendingDataPointsController extends Controller {

    @FXML
    private TableView<DataPoint> table;

    private ObservableList<DataPoint> data;

    public void initialize() throws SQLException {
        this.db = Main.getDb();
        db.rs = db.stmt.executeQuery("SELECT * FROM `Data_Point`");
        db.rs.beforeFirst();
        while (db.rs.next()) {
            DataPoint myPoint = new DataPoint();
            myPoint.setAccepted(db.rs.getBoolean("Accepted"));
            myPoint.setDataValue(db.rs.getInt("DataValue"));
            myPoint.setMyDate(db.rs.getDate("DateTime"));
            myPoint.setPointType(Type.valueOf(db.rs.getString("Type")));
            myPoint.setLocationName(db.rs.getString("LocationName"));
            data.add(myPoint);
        }
        table.setItems(data);
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

    }
}
