```java
// Memanggil paket folder login
package login;

// Library untuk menangani pengecualian yang terkait dengan operasi input/output.
import java.io.IOException;
// Library untuk koneksi ke database.
import java.sql.Connection;
// Library untuk mendapatkan koneksi ke database.
import java.sql.DriverManager;
// Library untuk mempersiapkan dan mengeksekusi pernyataan SQL yang telah di-precompile.
import java.sql.PreparedStatement;
// Library untuk merepresentasikan hasil dari eksekusi pernyataan SQL dan menyediakan metode untuk mengambil data dari hasil tersebut.
import java.sql.ResultSet;
// Library untuk pengecualian yang berkaitan dengan operasi database.
import java.sql.SQLException;
// Mengimport Class DashboardController dari package dashboard
import dashboard.DashboardController;
// Library ini digunakan untuk suatu kejadian (event) yang terjadi sebagai hasil dari tindakan pengguna, misalnya klik tombol.
import javafx.event.ActionEvent;
// Library untuk mendefinisikan antarmuka pengguna JavaFX.
import javafx.fxml.FXML;
// Library untuk mengload sebuah file format FXML
import javafx.fxml.FXMLLoader;
// Library untuk memanggil kelas dasar untuk semua node yang memiliki anak dalam hierarki node JavaFX.
import javafx.scene.Parent;
// Library untuk mewakili sebuah scene (layar) dalam aplikasi JavaFX.
import javafx.scene.Scene;
// Library untuk membuat sebuah Alert modal
import javafx.scene.control.Alert;
// Library untuk membuat teks label
import javafx.scene.control.Label;
// Library untuk membuat kolom password
import javafx.scene.control.PasswordField;
// Library untuk membuat kolom teks
import javafx.scene.control.TextField;
// Library untuk membuat sebuah jendela aplikasi
import javafx.stage.Stage;
// Library ini adalah sebuah interface umum untuk semua elemen node dalam hierarki scene JavaFX.
import javafx.scene.Node;


// Membuat sebuah class bernama LoginController
public class LoginController {
    // Membuat variable FXML untuk teks label, kolom password, dan kolom teks  dari file fxml
    @FXML
    private Label labelCheck;
    @FXML
    private PasswordField passwordInput;
    @FXML
    TextField usernameInput;

    // 1. 'root' bertipe Parent, digunakan untuk menyimpan node induk dari sebuah scene.
    // 2. 'scene' bertipe Scene, digunakan untuk merepresentasikan tampilan aplikasi JavaFX.
    // 3. 'stage' bertipe Stage, digunakan untuk merepresentasikan jendela aplikasi JavaFX.
    private Parent root;
    private Scene scene;
    private Stage stage;

    // Kami membuat sebuah sebuah variable DB_URL yang bersifat const dan private berisi link localhost database untuk db_barang
    private static final String DB_URL = "jdbc:mysql://localhost:3306/db_barang";
    // Kami membuat sebuah method submit untuk tombol submit pada elemen button di dalam file FXML, method tersebut digunakan untuk menjalankan kode di dalam method submit pada saat melakukan ActionEvent yaitu mengclick button.
    public void submit(ActionEvent event) throws IOException, ClassNotFoundException  {
        // Memanggil sebuah driver jdbc mysql, untuk mengtracking error jika pada saat driver tidak ditemukan, kami menggunakan try n catch agar bisa mendapatkan error yang jelas
        try {
            Class.forName(“com.mysql.jdbc.Driver”);
        } catch (ClassNotFoundException e) {
            System.out.println("Pesan Error : " + e)
        }
        // Kami membuat sebuah variable yang bertugas untuk mendapatkan hasil inputan dari user yaitu username dan password
        String username = usernameInput.getText();
        String password = passwordInput.getText();
        // Kami menggunakan if statement dan menggunakan method yang kami buat yaitu authenticateUser yang berisi parameter username dan password.
        // Jika username dan password terlacak di database, maka login akan berhasil lalu otomatis akan mengarah ke Dashboard dan menambahkan username di header atas
        if (authenticateUser(username, password)) {
            // Membuat sebuah log di terminal bahwa login sudah berhasil dan menampilkan ucapan selamat datang.
            System.out.println("Login berhasil!");
            System.out.println("Met datang, " + username + "!");
            // Mengubah dan mengload tampilan fxml menjadi Dashboard
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/dashboard/Dashboard.fxml"));
            root = loader.load();
            // Memanggil Controller dari Dashboard ke LoginController dan memanipulasi username dengan method displayName
            DashboardController dashboardController = loader.getController();
            dashboardController.displayName(username);
            // Variable stage ini mengambil sumber event (yang diharapkan sebagai elemen GUI), menavigasi hingga ke objek Stage yang mengelola jendela aplikasi.
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            // Lalu membuat objek Scene baru dengan root node (elemen teratas) yang diberikan
            scene = new Scene(root);
            // Mengatur objek Scene yang baru dibuat sebagai Scene untuk objek Stage.
            stage.setScene(scene);
            // Menampilkan jendela aplikasi dengan memanggil metode show() pada objek Stage. 
            stage.show();
        // Jika autentikasi gagal, maka akan menampilkan pesan error di GUI yang menyatakan bahwa username atau password salah 
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Gagal Login");
            alert.setHeaderText(null);
            alert.setContentText("username atau password salah");
            alert.showAndWait();
        }
    }

    // Kami membuat method dengan type data boolean dan bersifat private karena ini berketerkaitan dengan database.
    // Kami membuat method authenticateUser yang berisikan parameter username dan password.
    private boolean authenticateUser(String username, String password) {
        // Kami membuat sebuah try and catch yang fungsinya jika hasil inputan username dan password telah dimasukkan, maka java database akan memanggil DriverManager terlebih dahulu dan otomatis mencoba untuk memvalidasi apakah username dan password tersebut apakah ada atau tidak di dalam database. 
        // Connection digunakan untuk membuat koneksi ke database dengan menggunakan URL, nama user, dan kata sandi tertentu dengan bantuan DriverManager.
        try (Connection connection = DriverManager.getConnection(DB_URL, "root", "");
             // Kami memanggil library PreparedStatement pada java sql untuk mencari entri user berdasarkan username dan password dalam tabel database menggunakan kueri SQL yang telah disiapkan.
             // "SELECT * FROM `tabel_user` WHERE BINARY username = ? AND password = ?" maksudnya adalah kami ingin memerintah SQL untuk menselect semua item di dalam tabel_user dan mencari username dan password
             // Kami menggunakan BINARY pada username karena kami ingin data username yang dimasukkan itu case sensitive, jadi akan meningkatkan keamanan data.
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM `tabel_user` WHERE BINARY username = ? AND password = ?")) {
                // Kami membuat 2 setString yang dimana sebagai pengganti placeholder dari "?" dalam perintah SQL
                statement.setString(1, username);
                statement.setString(2, password);
                // Kami mencoba menjalankan kueri dengan menggunakan objek Statement dan ResultSet. Jika hasil kueri memiliki setidaknya satu baris, maka fungsi ini akan mengembalikan true, dan jika tidak ada baris, maka akan mengembalikan false. 
                try (ResultSet resultSet = statement.executeQuery()) {
                    return resultSet.next();
                }
            }
        // Jika tidak ada DriverManager, maka akan menampilkan error secara detail
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}

```
