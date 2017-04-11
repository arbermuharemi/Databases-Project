package main.java.controller;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import main.java.model.DatabaseRef;
import main.java.model.UserType;

import java.util.ArrayList;

/**
 * Created by Yash on 9/28/2016.
 * This class's purpose is to: <DESCRIBE PURPOSE>
 */
public class RegistrationScreenController {

    private Main myApp;
    private DatabaseRef db;

    @FXML
    private TextField userField;

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passField;

    @FXML
    private PasswordField confirmPassField;

    @FXML
    private ComboBox<UserType> typeBox;

    @FXML
    private ComboBox<String> stateBox;

    @FXML
    private ComboBox<String> cityBox;

    @FXML
    private TextField titleField;

    private ObservableList<UserType> typeList = FXCollections.observableArrayList(UserType.CITY_SCIENTIST, UserType.CITY_OFFICIAL);
    private ArrayList<String> states;
    private ObservableList<String> stateList;
    private ArrayList<String> cities;
    private ObservableList<String> cityList;

    @FXML
    private void initialize() throws Exception {
        typeBox.setItems(typeList);
        typeBox.setValue(UserType.CITY_SCIENTIST);
        this.db = Main.getDb();
        db.rs = db.stmt.executeQuery(
                "SELECT * "
                        + "FROM `City_State`");
        states = new ArrayList<>();
        cities = new ArrayList<>();
        db.rs.beforeFirst();
        while (db.rs.next()) {
            states.add(db.rs.getString("State"));
            cities.add(db.rs.getString("City"));
        }
        stateList = FXCollections.observableList(states);
        cityList = FXCollections.observableList(cities);
        stateBox.setItems(stateList);
    }

    public void setMainApp(Main mainApp, DatabaseRef db) {
        myApp = mainApp;
        //this.db = db;
    }


    @FXML
    private void handleRegisterSubmitPressed() throws Exception {
        String userName = userField.getText();
        String email = emailField.getText();
        String pass = passField.getText();
        String confirmPass = confirmPassField.getText();
        db.preparedStatement = db.conn.prepareStatement(
                "SELECT * "
                        + "FROM `User` "
                        + "WHERE Username = ? "
                        + "AND Password = ?");
        db.preparedStatement.setString(1, userField.getText());
        db.preparedStatement.setString(2, passField.getText());
        System.out.println(db.preparedStatement);
        db.rs = db.preparedStatement.executeQuery();
        if (db.rs.first()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("User Already Exists");
            alert.setContentText("A user with these credentials exists. "
                    + "If this account is yours navigate to the login screen "
                    + "and login with this account, or choose a different "
                    + "username");
            alert.showAndWait();
            return;
        }
        if (!pass.equals(confirmPass)) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Passwords Don't Match");
            alert.setContentText("The passwords you entered don't match. "
                    + "Please re-enter your passwords");
            alert.showAndWait();
            passField.clear();
            confirmPassField.clear();
            return;
        }

        db.preparedStatement = db.conn.prepareStatement(
                "INSERT INTO `User` ("
                        + "`Username` ,"
                        + "`EmailAddress` ,"
                        + "`Password` ,"
                        + "`UserType`"
                        + ")"
                        + "VALUES ("
                        + "?, ?, ?, ?"
                        + ")"
        );
        db.preparedStatement.setString(1, userField.getText());
        db.preparedStatement.setString(2, emailField.getText());
        db.preparedStatement.setString(3, passField.getText());
        db.preparedStatement.setString(4, typeBox.getValue().name());
        db.preparedStatement.executeUpdate();
        if (typeBox.getValue().equals(UserType.CITY_OFFICIAL)) {
            db.preparedStatement = db.conn.prepareStatement(
                    "INSERT INTO `City_Official` ("
                            + "`Username` ,"
                            + "`Title` ,"
                            + "`Approved` ,"
                            + "`City` ,"
                            + "`State`"
                            + ")"
                            + "VALUES ("
                            + "?, ?, NULL , ?, ?"
                            + ")"
            );
            db.preparedStatement.setString(1, userField.getText());
            db.preparedStatement.setString(2, titleField.getText());
            db.preparedStatement.setString(3, cityBox.getValue());
            db.preparedStatement.setString(4, stateBox.getValue());
            db.preparedStatement.executeUpdate();
        }
    }

    @FXML
    private void handleCancelPressed() {
        myApp.loadLogin();
    }

    @FXML
    private void changeCities() throws Exception {
        cities.clear();
        cityList.clear();
        //System.out.println("once");
        db.preparedStatement = db.conn.prepareStatement(
                "SELECT City FROM City_State WHERE State = ?");
        db.preparedStatement.setString(1, stateBox.getValue());
        System.out.println(db.preparedStatement);
        db.rs = db.preparedStatement.executeQuery();
        db.rs.beforeFirst();
        while (db.rs.next()) {
            cities.add(db.rs.getString("City"));
            //System.out.println(db.rs.getString("City"));
        }
        cityList = FXCollections.observableList(cities);
        cityBox.setItems(cityList);
    }


}
