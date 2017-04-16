package main.java.controller;

/**
 * Created by Ashwin Ignatius on 4/15/2017.
 */

import main.java.fxapp.Main;
import main.java.model.DatabaseRef;

public class Controller {
    Main myApp;
    DatabaseRef db;

    public void setMainApp(Main mainApp) {
        myApp = mainApp;
        this.db = Main.getDb();
    }
}
