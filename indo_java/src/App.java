import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

// Ini adalah class App, sebuah class utama dan inti dari sebuah aplikasi JavaFX
public class App extends Application {
    // Variabel untuk menyimpan root node dari UI
    Parent root;
    // Variabel untuk menyimpan scene dari UI
    Scene scene;
    // Metode utama untuk memulai aplikasi JavaFX
    @Override
    public void start(Stage stage) throws IOException {
        try {
            // Memuat UI dari file FXML menggunakan FXMLLoader
            root = FXMLLoader.load(getClass().getResource("/login/Login.fxml"));
            // Membuat objek Scene dengan root node
            scene = new Scene(root);
            // Mengatur judul jendela aplikasi
            stage.setTitle("Indo Java - Powered by ODGJ");
            // Mengatur scene ke dalam stage
            stage.setScene(scene);
            // Menampilkan stage
            stage.show();
            // Mengatur aksi ketika pengguna menutup aplikasi
            stage.setOnCloseRequest(e -> {
                e.consume(); // Mencegah penutupan otomatis
                Logout(stage); // Memanggil metode Logout untuk menangani proses logout
            });
        } catch (IOException e) {
            e.printStackTrace(); // Menangani eksepsi jika terjadi kesalahan saat memuat UI
        }
    }
    // Metode untuk menangani proses logout
    public void Logout(Stage stage) {
        // Membuat objek Alert dengan jenis konfirmasi
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Logout");
        alert.setHeaderText("Kamu akan Logout!");
        alert.setContentText("Apakah anda ingin keluar?");
        // Menampilkan dialog konfirmasi dan menanggapi tombol yang diklik pengguna
        if (alert.showAndWait().get() == ButtonType.OK) {
            System.out.println("Berhasil logout"); // Pesan ke konsol jika logout berhasil
            stage.close(); // Menutup aplikasi
        }
    }
    // Metode utama untuk menjalankan aplikasi
    public static void main(String[] args) {
        launch(args);
    }
}