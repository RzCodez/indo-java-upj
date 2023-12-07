package dashboard.transaksi;

import java.io.IOException;
// import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Session.Session;

import dashboard.DashboardController;
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
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class TransaksiController {
    @FXML
    private AnchorPane TransaksiPane;

    @FXML
    private AnchorPane keluarButton;

    @FXML
    Label namaUser;

    @FXML
    private AnchorPane transaksiButton;

    @FXML
    private AnchorPane dashboardButton;

    Parent root;
    Scene scene;
    Stage stage;

    // Variable of price barang
    @FXML
    Label minyakGorengPrice;
    @FXML
    Label sabunPrice;
    @FXML
    Label aquaPrice;
    @FXML
    Label mieGorengPrice;
    // End of variable of price barang

    // Variable of jumlah
    @FXML
    Label minyakGorengJumlah;
    @FXML
    Label sabunJumlah;
    @FXML
    Label aquaJumlah;
    @FXML
    Label mieGorengJumlah;
    // End of variable of jumlah

    // Variable of button
    @FXML
    VBox minyakGorengButton;
    @FXML
    VBox sabunButton;
    @FXML
    VBox aquaButton;
    @FXML
    VBox mieGorengButton;
    // End of variable of button

    private Connection connection;
    private static final String DB_URL = "jdbc:mysql://localhost:3306/db_barang";
    
    @FXML
    private int getHargaValue(int id_barang) throws SQLException {
        int hargaBarang = 0;
        try (Connection connection = DriverManager.getConnection(DB_URL, "root", "")) {
            PreparedStatement statement = connection.prepareStatement("SELECT `harga_barang` FROM `tabel_inventori` WHERE `id_barang` = ?");
            statement.setInt(1, id_barang);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    hargaBarang = resultSet.getInt("harga_barang");
                }
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
        return hargaBarang;
    }

    public void displayValue() throws SQLException {
        minyakGorengPrice.setText("Rp. " + getHargaValue(1));
        sabunPrice.setText("Rp. " + getHargaValue(2));
        aquaPrice.setText("Rp. " + getHargaValue(3));
        mieGorengPrice.setText("Rp. " + getHargaValue(4));
    }

    @FXML
    public void minyakGorengButton(MouseEvent event) {
        int jumlahMinyakGoreng = Integer.parseInt(minyakGorengJumlah.getText());
        jumlahMinyakGoreng++;
        minyakGorengJumlah.setText(String.valueOf(jumlahMinyakGoreng));
    }

    @FXML
    public void sabunButton(MouseEvent event) {
        int jumlahSabun = Integer.parseInt(sabunJumlah.getText());
        jumlahSabun++;
        sabunJumlah.setText(String.valueOf(jumlahSabun));
    }

    @FXML
    public void aquaButton(MouseEvent event) {
        int jumlahAqua = Integer.parseInt(aquaJumlah.getText());
        jumlahAqua++;
        aquaJumlah.setText(String.valueOf(jumlahAqua));
    }

    @FXML
    public void mieGorengButton(MouseEvent event) {
        int jumlahMieGoreng = Integer.parseInt(mieGorengJumlah.getText());
        jumlahMieGoreng++;
        mieGorengJumlah.setText(String.valueOf(jumlahMieGoreng));
    }
    
    @FXML
    public void bayarButton(MouseEvent event) throws SQLException {
        
    }

    public void displayName() {
        // LoginController loginController = new LoginController();
        String username = Session.getUsername();
        namaUser.setText(username);
    }



    public void dashboardCta(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/dashboard/Dashboard.fxml"));
        root = loader.load();
        DashboardController dashboardController = loader.getController();
        dashboardController.displayName();

        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void Logout(MouseEvent event) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Logout");
        alert.setHeaderText("Kamu akan Logout!");
        alert.setContentText("Apakah anda ingin keluar?");
        if (alert.showAndWait().get() == ButtonType.OK) {
            stage = (Stage) TransaksiPane.getScene().getWindow();
            System.out.println("Berhasil logout");
            stage.close();
        }
    }

    @FXML
    void initialize() throws SQLException {
        displayName();
        displayValue();
    }

}
