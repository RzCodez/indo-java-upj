package dashboard;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

public class DashboardController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    public static Button btnDashboard;

    @FXML
    void hover(MouseEvent event) {
        btnDashboard.setStyle("-fx-background-color: rgb(116, 54, 250, 0.3)");
    }

    @FXML
    void initialize() {
        assert btnDashboard != null : "fx:id=\"btnDashboard\" was not injected: check your FXML file 'Dashboard.fxml'.";
    }

}
