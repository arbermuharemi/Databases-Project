package main.java.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import main.java.model.DatabaseRef;
import main.java.model.UserType;

import java.io.File;

/**
 * Created by Arber on 4/10/2017.
 * This class's purpose is to: <DESCRIBE PURPOSE>
 */
public class LoginScreenController extends Controller {

    @FXML
    private TextField userField;

    @FXML
    private PasswordField passField;

    @FXML
    private void handleLoginPressed() throws Exception {
        System.out.println(db);
        System.out.println(db.preparedStatement);
        System.out.println(db.conn);
        db.preparedStatement = db.conn.prepareStatement("SELECT * FROM `User` WHERE Username = ? AND PASSWORD = ?");
        db.preparedStatement.setString(1, userField.getText());
        db.preparedStatement.setString(2, passField.getText());
        System.out.println(db.preparedStatement);
        db.rs = db.preparedStatement.executeQuery();
        if (!(db.rs.first())) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Credentials Error");
            alert.setContentText("No user with these credentials exists. Please reenter your information, or if you have not made an account, click 'Register'.");
            alert.showAndWait();
            return;
        }
        UserType userType = UserType.valueOf(db.rs.getString("UserType"));
        if (userType.equals(UserType.ADMIN)) {
            myApp.load(new File("../view/AdminHome.fxml"));
        } else if (userType.equals(UserType.CITY_OFFICIAL)) {
            db.preparedStatement = db.conn.prepareStatement("SELECT Approved FROM City_Official WHERE Username = ?");
            db.preparedStatement.setString(1, userField.getText());
            db.rs = db.preparedStatement.executeQuery();
            db.rs.first();
            if (!db.rs.getBoolean("Approved")) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Not Approved");
                alert.setContentText("Your account has either been rejected "
                        + "or it has not been approved yet. "
                        + "Please contact an administrator to approve "
                        + "your account");
                alert.showAndWait();
                return;
            }
            myApp.load(new File("../view/CityOfficialHome.fxml"));
        } else if (userType.equals(UserType.CITY_SCIENTIST)) {
            myApp.load(new File("../view/AddDataPoint.fxml"));
        }
    }

    @FXML
    private void handleRegisterPressed() {
        myApp.load(new File("../view/RegistrationScreen.fxml"));
    }
}

