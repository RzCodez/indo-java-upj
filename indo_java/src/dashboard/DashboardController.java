package dashboard;

import java.io.IOException;

import dashboard.transaksi.TransaksiController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import session.Session;

public class DashboardController {

    @FXML
    private AnchorPane DashboardPane;

    @FXML
    AnchorPane dashboardButton;

    @FXML
    AnchorPane transaksiButton;

    @FXML
    AnchorPane keluarButton;

    @FXML
    Label namaUser;

    Stage stage;
    Parent root;
    Scene scene;

    public void displayName() {
        String username = Session.getUsername();
        namaUser.setText(username);
    }

    public void transaksiCta(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("./transaksi/Transaksi.fxml"));
        root = loader.load();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);

        // Set Session sebelum memuat FXML
        Session.setUsername(namaUser.getText());

        // Ambil controller setelah FXML dimuat
        TransaksiController transaksiController = loader.getController();
        transaksiController.displayName();

        stage.show();
    }

    public void Logout(MouseEvent event) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Logout");
        alert.setHeaderText("Kamu akan Logout!");
        alert.setContentText("Apakah anda ingin keluar?");
        if (alert.showAndWait().get() == ButtonType.OK) {
            stage = (Stage) DashboardPane.getScene().getWindow();
            System.out.println("Berhasil logout");
            stage.close();
        }
    }

}
