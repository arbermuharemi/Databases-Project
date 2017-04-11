package main.java.controller;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import main.java.model.DatabaseRef;

import java.io.IOException;


public class Main extends Application {
    private Stage window;
    private DatabaseRef db;

    @Override
    public void start(Stage primaryStage) throws Exception {
        db = new DatabaseRef();
        db.connect();
        window = primaryStage;
        loadLogin();
    }

    public void loadLogin() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("../view/LoginScreen.fxml"));
            AnchorPane welcomeLayout = loader.load();

            LoginScreenController controller = loader.getController();
            controller.setMainApp(this, db);

            window.setTitle("Login Page");
            Scene welcomeScene = new Scene(welcomeLayout);
            window.setScene(welcomeScene);
            window.show();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public void loadRegister() {

    }


    public void loadAdminWelcome() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("../view/AdminWelcome.fxml"));
            AnchorPane adminLayout = loader.load();

            AdminWelcomeController controller = loader.getController();
            controller.setMainApp(this, db);

            window.setTitle("Admin Home Page");
            Scene adminScene = new Scene(adminLayout);
            window.setScene(adminScene);
            window.show();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public void loadCityOfficialWelcome() {

    }

    public void loadCityScientistWelcome() {

    }

        //if(!conn.isClosed())
    //    System.out.println("Successfully connected to " +
            //            "MySQL server using TCP/IP...");
            //stmt = conn.createStatement();

    //        db.rs = db.stmt.executeQuery("SELECT * FROM POI");
    //        db.rs.beforeFirst();
    //        int num = 1;
    //        while (db.rs.next()) {
    //            System.out.println("Location " + num + ": " + db.rs.getString("LocationName"));
    //            num++;
    //        }
    //    } catch (Exception e) {
    //        System.err.println("Exception: " + e.getMessage());
    //        //} finally {
    //        //    try {
    //        //        if(db.conn != null) {
    //        //            conn.close();
    //        //        }
    //        //    } catch(SQLException e) {}
    //        //}
    //    }
    //    Parent root = FXMLLoader.load(getClass().getResource("LoginScreen.fxml"));
    //    primaryStage.setTitle("Login");
    //    primaryStage.setScene(new Scene(root));
    //    primaryStage.show();
    //    this.window = primaryStage;
    //    System.out.println(window);
    //}

    @FXML
    public void handleLoginPressed() throws Exception {
        //System.out.println(db);
        //System.out.println(db.preparedStatement);
        //System.out.println(db.conn);
        //db.preparedStatement = db.conn.prepareStatement("SELECT * FROM `User` WHERE Username = ? AND PASSWORD = ?");
        //db.preparedStatement.setString(1, userField.getText());
        //db.preparedStatement.setString(2, passField.getText());
        //System.out.println(db.preparedStatement);
        //db.rs = db.preparedStatement.executeQuery();
        //if (!(db.rs.first())) {
        //    Alert alert = new Alert(Alert.AlertType.INFORMATION);
        //    alert.setTitle("Credentials Error");
        //    alert.setContentText("No user with these credentials exists. Please reenter your information, or if you have not made an account, click 'Register'.");
        //    alert.showAndWait();
        //    return;
        //}
        //FXMLLoader loader = new FXMLLoader();
        //loader.setLocation(Main.class.getResource("src2/sample.fxml"));
        //BorderPane welcomeLayout = loader.load();
        //System.out.println(window);
        //window.setTitle("Welcome Page");
        //Scene welcomeScene = new Scene(welcomeLayout);
        //window.setScene(welcomeScene);
        //window.show();
    }

    public void handleRegisterPressed() {

    }

    public static void main(String[] args) {
        launch(args);
    }
}
