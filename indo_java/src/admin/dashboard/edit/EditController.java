package admin.dashboard.edit;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Session.Session;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class EditController {
    @FXML
    AnchorPane EditPane;

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

    public void showStock() throws SQLException{
        minyakGorengForm.setText(String.valueOf(getValue(1)));
        sabunForm.setText(String.valueOf(getValue(2)));
        aquaForm.setText(String.valueOf(getValue(3)));
        mieGorengForm.setText(String.valueOf(getValue(4)));
    }

    @FXML
    TextField minyakGorengForm;
    @FXML
    TextField sabunForm;
    @FXML
    TextField aquaForm;
    @FXML
    TextField mieGorengForm;

    public void submitForm(MouseEvent event) {
        String minyakGoreng = minyakGorengForm.getText();
        String sabun = sabunForm.getText();
        String aqua = aquaForm.getText();
        String mieGoreng = mieGorengForm.getText();
    
        try (Connection connection = DriverManager.getConnection(DB_URL, "root", "");) {
            PreparedStatement statement = connection.prepareStatement("UPDATE `tabel_inventori` SET `stok_barang` = ? WHERE `id_barang` = ?");
            
            statement.setString(1, minyakGoreng);
            statement.setInt(2, 1);
            statement.addBatch();
            
            statement.setString(1, sabun);
            statement.setInt(2, 2);
            statement.addBatch();
            
            statement.setString(1, aqua);
            statement.setInt(2, 3);
            statement.addBatch();
            
            statement.setString(1, mieGoreng);
            statement.setInt(2, 4);
            statement.addBatch();
            
            statement.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Tempat untuk CTA
    public void dashboardCta(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/admin/dashboard/AdminDashboard.fxml"));
        root = loader.load();

        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
    public void transaksiCta(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/admin/dashboard/transaksi/AdminTransaksi.fxml"));
        root = loader.load();

        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    // Menghandle aksi klik tombol keluar (logout)
    public void Logout(MouseEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Logout");
        alert.setHeaderText("Kamu akan Logout!");
        alert.setContentText("Apakah anda ingin keluar?");
        if (alert.showAndWait().get() == ButtonType.OK) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/login/Login.fxml"));
            // Memuat FXML file dan mendapatkan root node dari tata letak yang dihasilkan
            root = loader.load();
            // Mendapatkan stage dari event source (tombol yang diklik) dan mengkonversinya
            // menjadi objek Stage
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            // Membuat objek Scene baru menggunakan root node yang telah dimuat
            scene = new Scene(root);
            // Mengatur scene pada stage
            stage.setScene(scene);
            // Menampilkan stage yang telah dikonfigurasi
            stage.show();
        }
    }
    
    // End of tempat untuk CTA
    
    @FXML
    Label namaUser;
    public void displayName() {
        String username = Session.getUsername();
        namaUser.setText(username);
    }

    @FXML
    void initialize() throws SQLException {
        displayName();
        showStock();
    }
}
