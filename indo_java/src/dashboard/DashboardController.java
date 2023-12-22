package dashboard;

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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

// ini adalah class DashboardController, sebuah class untuk mengurus aktifitas dashboard
public class DashboardController {

    // GridPane untuk menampilkan data barang
    @FXML
    GridPane barang_display;

    // AnchorPane untuk tombol navigasi dashboard
    @FXML
    AnchorPane dashboardButton;

    // AnchorPane untuk tombol navigasi transaksi
    @FXML
    AnchorPane transaksiButton;

    // AnchorPane untuk tombol navigasi keluar
    @FXML
    AnchorPane keluarButton;

    // Label untuk menampilkan nama pengguna yang login
    @FXML
    Label namaUser;

    // Variabel stok barang
    @FXML
    Label minyakGorengValue;
    @FXML
    Label sabunValue;
    @FXML
    Label aquaValue;
    @FXML
    Label mieGorengValue;

    // Variabel untuk menampilkan total uang
    @FXML
    Label totalUang;

    // Variabel untuk mengelola stage, root, dan scene
    Stage stage;
    Parent root;
    Scene scene;

    // Koneksi ke database menggunakan JDBC
    private Connection connection;
    private static final String DB_URL = "jdbc:mysql://localhost:3306/db_barang";

    // Mendapatkan nilai stok barang dari database berdasarkan id_barang
    private int getValue(int id_barang) throws SQLException {
        // Membuka koneksi ke database menggunakan JDBC
        try (Connection connection = DriverManager.getConnection(DB_URL, "root", "")) {
            // Membuat prepared statement untuk menjalankan query SQL
            PreparedStatement statement = connection
                    .prepareStatement("SELECT `stok_barang` FROM `tabel_inventori` WHERE `id_barang` = ?");

            // Mengatur nilai parameter pada prepared statement
            statement.setInt(1, id_barang);

            // Menjalankan query SQL dan mendapatkan hasilnya
            try (ResultSet resultSet = statement.executeQuery()) {
                // Jika ada hasil, mengembalikan nilai stok_barang
                if (resultSet.next()) {
                    return resultSet.getInt("stok_barang");
                }
            }
        } catch (SQLException e) {
            // Menangani exception jika terjadi kesalahan dalam mendapatkan nilai barang
            throw new SQLException("Gagal mendapatkan nilai barang", e);
        } finally {
            // Menutup koneksi ke database setelah penggunaan selesai
            if (connection != null) {
                connection.close();
            }
        }
        // Mengembalikan nilai default (0) jika tidak ada hasil dari query
        return 0;
    }

    // Mendapatkan total uang dari database dan menampilkan di GUI
    private int getTotalUang() throws SQLException {
        // Membuka koneksi ke database menggunakan JDBC
        try (Connection connection = DriverManager.getConnection(DB_URL, "root", "")) {
            // Membuat pernyataan SQL untuk mengambil total_uang dari tabel_keuangan
            PreparedStatement statement = connection.prepareStatement("SELECT `total_uang` FROM `tabel_keuangan`");

            // Inisialisasi variabel value untuk menyimpan hasil query
            int value = 0;

            // Mengeksekusi query dan mendapatkan hasilnya
            try (ResultSet resultSet = statement.executeQuery()) {
                // Jika ada hasil, ambil nilai total_uang
                if (resultSet.next()) {
                    value = resultSet.getInt("total_uang");
                }
            }

            // Menampilkan nilai total_uang di GUI dengan format mata uang
            totalUang.setText("Rp. " + String.valueOf(value));
        } catch (SQLException e) {
            // Tangkap exception dan lemparkan kembali dengan pesan yang sesuai
            throw new SQLException("Gagal mendapatkan total uang", e);
        } finally {
            // Tutup koneksi ke database dalam blok finally agar selalu terpanggil
            if (connection != null) {
                connection.close();
            }
        }
        // Mengembalikan nilai 0 (mungkin tidak diperlukan, tergantung kebutuhan
        // aplikasi)
        return 0;
    }

    // Menampilkan stok barang di GUI
    public void showStock() throws SQLException {
        minyakGorengValue.setText(getValue(1) + " barang");
        sabunValue.setText(getValue(2) + " barang");
        aquaValue.setText(getValue(3) + " barang");
        mieGorengValue.setText(getValue(4) + " barang");
    }

    // Metode untuk menghandle aksi klik tombol transaksi dan menavigasi ke halaman
    // transaksi
    public void transaksiCta(MouseEvent event) throws IOException {
        // Membuat objek FXMLLoader untuk memuat FXML file Transaksi.fxml
        FXMLLoader loader = new FXMLLoader(getClass().getResource("./transaksi/Transaksi.fxml"));
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

    // Menampilkan nama pengguna yang login saat aplikasi dimuat
    public void displayName() {
        String username = Session.getUsername();
        namaUser.setText(username);
    }

    // Metode yang dipanggil saat menginisialisasi controller (saat aplikasi dimuat)
    @FXML
    public void initialize() throws SQLException {
        displayName();
        showStock();
        getTotalUang();
    }
}
