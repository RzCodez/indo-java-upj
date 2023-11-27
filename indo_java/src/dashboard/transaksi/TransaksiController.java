package dashboard.transaksi;

import dashboard.DashboardController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import session.Session;

public class TransaksiController {
    @FXML
    private AnchorPane TransaksiPane;

    @FXML
    private AnchorPane keluarButton;

    @FXML
    private Label namaUser;

    @FXML
    private AnchorPane transaksiButton;

    // Metode initialize akan dipanggil setelah FXML dimuat
    @FXML
    public void initialize() {
        displayName();
    }

    public void displayName() {
        String username = Session.getUsername();
        namaUser.setText(username);
    }

}
