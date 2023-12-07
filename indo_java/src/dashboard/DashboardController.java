package dashboard;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Session.Session;

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
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class DashboardController {

    @FXML
    private AnchorPane DashboardPane;

    @FXML
    GridPane barang_display;

    @FXML
    AnchorPane dashboardButton;

    @FXML
    AnchorPane transaksiButton;

    @FXML
    AnchorPane keluarButton;

    // Login User variable
    @FXML
    Label namaUser;
    // End of login user variable

    // Variable of stok barang
    @FXML
    Label minyakGorengValue;

    @FXML
    Label sabunValue;

    @FXML
    Label aquaValue;

    @FXML
    Label mieGorengValue;
    // End of variable of stok barang

    // Variable of total uang
    @FXML
    Label totalUang;
    // End of variable of total uang

    Stage stage;
    Parent root;
    Scene scene;

    private Connection connection;
    private static final String DB_URL = "jdbc:mysql://localhost:3306/db_barang";

    private int getValue(int id_barang) throws SQLException {
        try (Connection connection = DriverManager.getConnection(DB_URL, "root", "");) {
            PreparedStatement statement = connection.prepareStatement("SELECT `stok_barang` FROM `tabel_inventori` WHERE `id_barang` = ?");
    
            statement.setInt(1, id_barang);
    
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("stok_barang");
                }
            }
        } catch (SQLException e) {
            throw new SQLException("Gagal mendapatkan nilai barang", e);
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
        return 0;
    }

    private int getTotalUang() throws SQLException {
        try (Connection connection = DriverManager.getConnection(DB_URL, "root", "");) {
            PreparedStatement statement = connection.prepareStatement("SELECT `total_uang` FROM `tabel_keuangan`");

            int value = 0;
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    value = resultSet.getInt("total_uang");
                }
            }
            
            totalUang.setText("Rp. " + String.valueOf(value));
        } catch (SQLException e) {
            throw new SQLException("Gagal mendapatkan total uang", e);
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
        return 0;
    }

    public void showStock() throws SQLException{
        minyakGorengValue.setText(getValue(1) + " barang");
        sabunValue.setText(getValue(2) + " barang");
        aquaValue.setText(getValue(3) + " barang");
        mieGorengValue.setText(getValue(4) + " barang");
    }

    public void transaksiCta(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("./transaksi/Transaksi.fxml"));
        root = loader.load();
        TransaksiController transaksiController = loader.getController();
        transaksiController.displayName();

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
            stage = (Stage) DashboardPane.getScene().getWindow();
            System.out.println("Berhasil logout");
            stage.close();
        }
    }

    public void displayName() {
        String username = Session.getUsername();
        namaUser.setText(username);
    }

    @FXML
    public void initialize() throws SQLException {
        displayName();
        showStock();
        getTotalUang();
    }

}
