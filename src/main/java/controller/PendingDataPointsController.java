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

/**
 * Created by Ashwin Ignatius on 4/21/2017.
 */
public class PendingDataPointsController extends Controller {

    @FXML
    private TableView<DataPoint> table;

    private ObservableList<TableColumn> myCols;

    @Override
    public void initialize() throws SQLException {
        this.db = Main.getDb();
        db.rs = db.stmt.executeQuery("SELECT * FROM `Data_Point`");
        myCols = table.getColumns();
        table.getR

        for (int i = 0; i < )
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
