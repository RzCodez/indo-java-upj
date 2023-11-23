```java
package login;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

// Pada baris kode di atas, kita mengimport beberapa kelas dan paket yang akan digunakan dalam file ini.
// Penjelasan untuk setiap import di atas:
// - java.net.URL: Mengimport kelas URL dari paket java.net. Kelas URL digunakan untuk merepresentasikan URL (Uniform Resource Locator) dalam jaringan.
// - java.util.ResourceBundle: Mengimport kelas ResourceBundle dari paket java.util. Kelas ResourceBundle digunakan untuk mengelola pengaturan lokal dalam aplikasi.
// - javafx.event.ActionEvent: Mengimport kelas ActionEvent dari paket javafx.event. Kelas ActionEvent digunakan untuk menangani peristiwa tindakan (action events) seperti klik tombol.
// - javafx.fxml.FXML: Mengimport anotasi FXML dari paket javafx.fxml. Anotasi FXML digunakan untuk menandai elemen yang didefinisikan dalam file FXML agar dapat dihubungkan dengan kode Java.
// - javafx.scene.control.Label: Mengimport kelas Label dari paket javafx.scene.control. Kelas Label digunakan untuk menampilkan teks atau gambar dengan format yang ditentukan.
// - javafx.scene.control.PasswordField: Mengimport kelas PasswordField dari paket javafx.scene.control. Kelas PasswordField digunakan untuk mengumpulkan input kata sandi dari pengguna.
// - javafx.scene.control.TextField: Mengimport kelas TextField dari paket javafx.scene.control. Kelas TextField digunakan untuk mengumpulkan input teks dari pengguna.

// LoginController adalah sebuah class utama dari sebuah file yang bernama LoginController.java
public class LoginController {

    @FXML
    private ResourceBundle resources;
    // ResourceBundle digunakan untuk mengelola pengaturan lokal dalam aplikasi.

    @FXML
    private URL location;
    // URL digunakan untuk merepresentasikan URL (Uniform Resource Locator) dalam jaringan.

    @FXML
    private Label labelCheck;
    // LabelCheck digunakan untuk menampilkan label teks dengan format yang ditentukan.

    @FXML
    private PasswordField passwordInput;
    // PasswordField digunakan untuk mengumpulkan input kata sandi dari pengguna.

    @FXML
    private TextField usernameInput;
    // TextField digunakan untuk mengumpulkan input teks dari pengguna.

    @FXML
    // Metode ini digunakan untuk menangani peristiwa tindakan (action events) seperti klik tombol.
    public void submit(ActionEvent event) {
        // Kami menggunakan String untuk username dan password karena semua username dan password adalah berbentuk teks, tidak ada nomor.
        String username = usernameInput.getText();
        String password = passwordInput.getText();
        
        // Kami menggunakan if-else untuk mengecek apakah username dan password sesuai atau tidak.
        if (username.equals("admin") && password.equals("admin")) {
            // Jika username dan password sesuai, kami akan menampilkan labelCheck dengan bertuliskan Login Success dan berwarna hijau.
            labelCheck.setText("Login Success");
            labelCheck.setTextFill(javafx.scene.paint.Color.GREEN);
        } else {
            // Jika username dan password tidak sesuai, kami akan menampilkan labelCheck dengan bertuliskan Login Failed dan berwarna merah.
            labelCheck.setText("*Login Failed");
            labelCheck.setTextFill(javafx.scene.paint.Color.RED);
        }
    }
}

```