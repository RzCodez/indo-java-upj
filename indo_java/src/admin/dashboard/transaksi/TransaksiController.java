package admin.dashboard.transaksi;

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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class TransaksiController {

    @FXML
    Label namaUser;

    private Parent root;
    private Scene scene;
    private Stage stage;

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

    // variable koneksi
    private Connection connection;
    private static final String DB_URL = "jdbc:mysql://localhost:3306/db_barang";

    @FXML
    // Method untuk mendapatkan harga barang
    private int getHargaValue(int id_barang) throws SQLException {
        int hargaBarang = 0;
        try (Connection connection = DriverManager.getConnection(DB_URL, "root", "")) {
            PreparedStatement statement = connection
                    .prepareStatement("SELECT `harga_barang` FROM `tabel_inventori` WHERE `id_barang` = ?");
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

    // Method untuk mencetak harga barang dari method getHargaValue
    public void displayValue() throws SQLException {
        minyakGorengPrice.setText("Rp. " + getHargaValue(1));
        sabunPrice.setText("Rp. " + getHargaValue(2));
        aquaPrice.setText("Rp. " + getHargaValue(3));
        mieGorengPrice.setText("Rp. " + getHargaValue(4));
    }

    //Method untuk mendapatkan value stok barang
    private int getValue(int id_barang) throws SQLException {
        try (Connection connection = DriverManager.getConnection(DB_URL, "root", "");) {
            PreparedStatement statement = connection
                    .prepareStatement("SELECT `stok_barang` FROM `tabel_inventori` WHERE `id_barang` = ?");

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
    
    //Variabel untuk menyimpan value dari hasil increment
    private int jumlahMinyakGoreng = 0;
    private int jumlahSabun = 0;
    private int jumlahAqua = 0;
    private int jumlahMieGoreng = 0;
    private int totalHarga = 0;

    //Method untuk tombol card minyak goreng
    @FXML
    public void minyakGorengButton(MouseEvent event) throws SQLException {
        int minyakGorengStock = getValue(1);
        if (minyakGorengStock < 1) {
            System.out.println("Minyak goreng habis");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Stock habis");
            alert.setContentText("Minyak goreng habis");
            alert.showAndWait();
        } else {
            jumlahMinyakGoreng++;
            minyakGorengJumlah.setText(String.valueOf(jumlahMinyakGoreng));
            totalHarga += getHargaValue(1);
        }
    }

    //Method untuk tombol card sabun 
    @FXML
    public void sabunButton(MouseEvent event) throws SQLException {
        int sabunStock = getValue(2);
        if (sabunStock == 0) {
            System.out.println("Sabun habis");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Stock habis");
            alert.setContentText("Sabun habis");
            alert.showAndWait();
        } else {
            jumlahSabun++;
            sabunJumlah.setText(String.valueOf(jumlahSabun));
            totalHarga += getHargaValue(2);
        }
    }

    
    //Method untuk tombol card aqua
    @FXML
    public void aquaButton(MouseEvent event) throws SQLException {
        int aquaStock = getValue(3);
        if (aquaStock == 0) {
            System.out.println("Aqua habis");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Stock habis");
            alert.setContentText("Aqua habis");
            alert.showAndWait();
        } else{
            jumlahAqua++;
            aquaJumlah.setText(String.valueOf(jumlahAqua));
            totalHarga += getHargaValue(3);
        }
    }

    //Method untuk tombol card mie goreng
    @FXML
    public void mieGorengButton(MouseEvent event) throws SQLException {
        int mieGorengStock = getValue(4);
        if (mieGorengStock == 0) {
            System.out.println("Mie goreng habis");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Stock habis");
            alert.setContentText("Mie goreng habis");
            alert.showAndWait();
        } else {
            jumlahMieGoreng++;
            mieGorengJumlah.setText(String.valueOf(jumlahMieGoreng));
            totalHarga += getHargaValue(4);
        }
    }

    // Method untuk update stok ke database
    public void updateStock() throws SQLException {
        try (Connection connection = DriverManager.getConnection(DB_URL, "root", "")) {
            PreparedStatement statement = connection.prepareStatement(
                    "UPDATE `tabel_inventori` SET `stok_barang` = `stok_barang` - ? WHERE `id_barang` = ?");
            statement.setInt(1, jumlahMinyakGoreng);
            statement.setInt(2, 1);
            statement.addBatch();
            statement.setInt(1, jumlahSabun);
            statement.setInt(2, 2);
            statement.addBatch();
            statement.setInt(1, jumlahAqua);
            statement.setInt(2, 3);
            statement.addBatch();
            statement.setInt(1, jumlahMieGoreng);
            statement.setInt(2, 4);
            statement.addBatch();

            statement.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    

    @FXML
    // Method untuk update keuangan dari hasil transaksi
    private void updateKeuangan() throws SQLException {
        // Retrieve the current value of total_uang from the database
        int currentTotalUang = 0;
        try (Connection connection = DriverManager.getConnection(DB_URL, "root", "")) {
            PreparedStatement statement = connection.prepareStatement("SELECT `total_uang` FROM `tabel_keuangan`");
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    currentTotalUang = resultSet.getInt("total_uang");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        int newTotalUang = currentTotalUang + totalHarga;

        try (Connection connection = DriverManager.getConnection(DB_URL, "root", "")) {
            PreparedStatement statement = connection.prepareStatement("UPDATE `tabel_keuangan` SET `total_uang` = ?");
            statement.setInt(1, newTotalUang);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    // Method untuk tombol bayar
    public void bayarButton(MouseEvent event) throws SQLException, IOException {
        updateStock();
        updateKeuangan();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Transaksi");
        alert.setHeaderText("Total yang harus dibayarkan: ");
        alert.setContentText("Rp. " + totalHarga);
        if (alert.showAndWait().get() == ButtonType.OK) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/admin/dashboard/transaksi/AdminTransaksi.fxml"));
            root = loader.load();

            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }

    // Method untuk menampilkan username
    public void displayName() {
        String username = Session.getUsername();
        namaUser.setText(username);
    }

    // Menghandle aksi klik tombol dashboard
    public void dashboardCta(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/admin/dashboard/AdminDashboard.fxml"));
        root = loader.load();

        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    // Menghandle aksi klik tombol edit
    public void editCta(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/admin/dashboard/edit/AdminEdit.fxml"));
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

    @FXML
    void initialize() throws SQLException {
        displayName();
        displayValue();
    }
}
