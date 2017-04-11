import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import main.java.controller.Main;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Yash on 9/20/2016.
 * This class's purpose is to: <DESCRIBE PURPOSE>
 */
public class WelcomeController implements Initializable {

    private Main myApp;

    public void initialize(URL location, ResourceBundle resources) {}

    public void setMainApp(Main mainApp) {
        myApp = mainApp;
    }

    @FXML
    private void handleLoginPressed() {
        myApp.loadLogin();
    }

    @FXML
    private void handleRegisterPressed() {
        myApp.loadRegister();
    }

}
