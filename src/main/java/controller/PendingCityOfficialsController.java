package main.java.controller;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.util.Callback;
import main.java.fxapp.Main;
import main.java.model.CityOfficial;
import main.java.model.DataPoint;
import main.java.model.Type;

import java.io.File;
import java.sql.SQLException;

/**
 * Created by Arber on 4/19/2017.
 * This class's purpose is to: <DESCRIBE PURPOSE>
 */
public class PendingCityOfficialsController extends Controller {

    @FXML
    private TableView<CityOfficial> table;

    private TableColumn usernameCol;

    private TableColumn emailCol;

    private TableColumn cityCol;

    private TableColumn stateCol;

    private TableColumn titleCol;

    private ObservableList<CityOfficial> data = FXCollections.observableArrayList();

    public void initialize() throws SQLException {
        this.db = Main.getDb();
        table.getSelectionModel().setSelectionMode(
                SelectionMode.MULTIPLE
        );
        usernameCol = new TableColumn("Username");
        emailCol = new TableColumn("Email");
        cityCol = new TableColumn("City");
        stateCol = new TableColumn("State");
        titleCol = new TableColumn("Title");

        usernameCol.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures, ObservableValue>() {
                    @Override
                    public ObservableValue call(TableColumn.CellDataFeatures dataFeatures) {
                        CityOfficial cityOfficial = (CityOfficial) dataFeatures.getValue();
                        return new SimpleStringProperty(cityOfficial.getUsername());
                    }
                }
        );

        emailCol.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures, ObservableValue>() {
                    @Override
                    public ObservableValue call(TableColumn.CellDataFeatures dataFeatures) {
                        CityOfficial cityOfficial = (CityOfficial) dataFeatures.getValue();
                        return new SimpleStringProperty(cityOfficial.getEmail());
                    }
                }
        );

        cityCol.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures, ObservableValue>() {
                    @Override
                    public ObservableValue call(TableColumn.CellDataFeatures dataFeatures) {
                        CityOfficial cityOfficial = (CityOfficial) dataFeatures.getValue();
                        return new SimpleStringProperty(cityOfficial.getCity());
                    }
                }
        );

        stateCol.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures, ObservableValue>() {
                    @Override
                    public ObservableValue call(TableColumn.CellDataFeatures dataFeatures) {
                        CityOfficial cityOfficial = (CityOfficial) dataFeatures.getValue();
                        return new SimpleStringProperty(cityOfficial.getState());
                    }
                }
        );

        titleCol.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures, ObservableValue>() {
                    @Override
                    public ObservableValue call(TableColumn.CellDataFeatures dataFeatures) {
                        CityOfficial cityOfficial = (CityOfficial) dataFeatures.getValue();
                        return new SimpleStringProperty(cityOfficial.getTitle());
                    }
                }
        );

        db.rs = db.stmt.executeQuery(
                "SELECT `Username` , `EmailAddress`, "
                        + "`City` , `State` , `Title` "
                        + "FROM City_Official "
                        + "NATURAL JOIN User "
                        + "WHERE Approved IS NULL");
        db.rs.beforeFirst();
        while (db.rs.next()) {
            CityOfficial cityOfficial = new CityOfficial();
            cityOfficial.setUsername(db.rs.getString("Username"));
            cityOfficial.setEmail(db.rs.getString("EmailAddress"));
            cityOfficial.setTitle(db.rs.getString("Title"));
            cityOfficial.setCity(db.rs.getString("City"));
            cityOfficial.setState(db.rs.getString("State"));
            data.add(cityOfficial);
        }

        table.setItems(data);
        table.getColumns().addAll(usernameCol, emailCol,
                cityCol, stateCol, titleCol);
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
        for (CityOfficial o : table.getSelectionModel().getSelectedItems()) {
            System.out.println(o);
        }
    }

}
