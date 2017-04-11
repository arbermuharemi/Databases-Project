package main.java.controller;

import main.java.model.DatabaseRef;

/**
 * Created by Arber on 4/10/2017.
 * This class's purpose is to: <DESCRIBE PURPOSE>
 */
public class AdminWelcomeController {
    private Main myApp;
    private DatabaseRef db;

    public void setMainApp(Main mainApp) {
        myApp = mainApp;
        this.db = Main.getDb();
    }

}
