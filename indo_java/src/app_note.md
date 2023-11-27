```java
import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

// Import package java.io.IOException untuk menghandle exception saat melakukan operasi input/output
// Import package javafx.application.Application untuk membuat aplikasi JavaFX
// Import class FXMLLoader dari package javafx.fxml untuk memuat file FXML
// Import class Parent dari package javafx.scene untuk merepresentasikan node induk dari sebuah scene
// Import class Scene dari package javafx.scene untuk merepresentasikan tampilan aplikasi JavaFX
// Import class Alert dari package javafx.scene.control untuk membuat dialog box
// Import class ButtonType dari package javafx.scene.control untuk merepresentasikan tombol pada dialog box
// Import class Stage dari package javafx.stage untuk merepresentasikan jendela aplikasi JavaFX



public class App extends Application {
    // 1. 'root' bertipe Parent, digunakan untuk menyimpan node induk dari sebuah scene.
    // 2. 'scene' bertipe Scene, digunakan untuk merepresentasikan tampilan aplikasi JavaFX.
    // 3. 'stage' bertipe Stage, digunakan untuk merepresentasikan jendela aplikasi JavaFX.
    private Parent root;
    private Scene scene;
    private Stage stage;
    // Menggunakan anotasi @Override untuk menandakan bahwa metode ini merupakan peng-override metode dari kelas induknya (Application).
    @Override
    // Metode 'start' adalah metode yang dijalankan saat aplikasi JavaFX dimulai.
    // Metode ini menerima parameter bertipe Stage yang digunakan untuk merepresentasikan jendela aplikasi JavaFX.
    public void start(Stage stage) throws Exception {
        try {
            // Memuat file FXML dengan menggunakan FXMLLoader dan mengassign hasilnya ke variabel 'root'
            root = FXMLLoader.load(getClass().getResource("/dashboard/Dashboard.fxml"));
            // Membuat objek Scene dengan menggunakan 'root' sebagai node induk
            scene = new Scene(root);
            // Mengatur judul jendela aplikasi
            stage.setTitle("Indo Java - Powered by ODGJ");
            // Mengatur Scene pada Stage
            stage.setScene(scene);
            // Menampilkan jendela aplikasi
            stage.show();
            // Menangani event ketika jendela aplikasi ditutup
            stage.setOnCloseRequest(e -> {
                // e.consume() akan menghentikan event handler dari memproses event lebih lanjut.
                e.consume();
                // Memanggil method Logout dengan parameter stage
                Logout(stage);
            });
        // Jika ada error maka error tersebut akan dicatch dan ditampilkan apa yang error dalam kompilasi
        } catch (IOException e) {
            // Mencetak dan melacak lokasi error pada java
            e.printStackTrace();
        }
    }
    // Method untuk Logout dengan parameter stage
    public void Logout(Stage stage) {
        // Menggunakan Method Alert dari JavaFX untuk membuat modal Alert Konfirmasi
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        // Membuat title pada Alert
        alert.setTitle("Logout");
        // Membuat heading text dalam modal
        alert.setHeaderText("Kamu akan Logout!");
        // Membuat deskripsi/konten teks dalam modal
        alert.setContentText("Apakah anda ingin keluar?");

        // Jika Menekan tombol OK pada Modal, akan menutup program secara menyeluruh
        if (alert.showAndWait().get() == ButtonType.OK) {
            System.out.println("Berhasil logout");
            stage.close();
        }
    }

    // Tujuan dari kode di bawah ini adalah untuk memulai aplikasi JavaFX. 
    // Method `main` adalah entry point dari aplikasi JavaFX, yang akan dijalankan saat aplikasi dimulai.
    // Pada baris pertama `launch(args)`, method `launch` digunakan untuk menjalankan aplikasi JavaFX dengan mengambil argumen `args` dari command line.
    public static void main(String[] args) {
        launch(args);
    }
}


```
