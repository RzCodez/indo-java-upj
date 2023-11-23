package dashboard;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

public class Dashboard {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnDashboard;

    @FXML
    void hover(MouseEvent event) {
        btnDashboard.setStyle("-fx-background-color: rgb(116, 54, 250, 0.3)");
    }

    @FXML
    void initialize() {

    }

}
