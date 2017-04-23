package main.java.fxapp;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import jdk.nashorn.internal.ir.IfNode;
import main.java.controller.Controller;
import main.java.controller.POIDetailController;
import main.java.model.DataPoint;
import main.java.model.DatabaseRef;
import main.java.model.POI;

import java.io.File;
import java.io.IOException;
import java.lang.Character;


public class Main extends Application {
    private Stage window;
    private static DatabaseRef db;

    @Override
    public void start(Stage primaryStage) throws Exception {
        db = new DatabaseRef();
        db.connect();
        window = primaryStage;
        load(new File("../view/LoginScreen.fxml"));
    }

    public void load(File file) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("../view/" + file.getName()));
            AnchorPane sceneLayout = loader.load();

            Controller controller = loader.getController();
            controller.setMainApp(this);

            window.setTitle("Databases-Project");
            window.setScene(new Scene(sceneLayout));
            window.show();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void loadPOIDetail(String location) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("../view/POIDetail.fxml"));
            AnchorPane sceneLayout = loader.load();

            POIDetailController controller = loader.getController();
            controller.setMainApp(this);
            controller.setLocation(location);

            window.setTitle("Databases-Project");
            window.setScene(new Scene(sceneLayout));
            window.show();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
    
    public static DatabaseRef getDb() {
        return db;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
