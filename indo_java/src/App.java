import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

// Kami mengimport library yang bermacam-macam seperti IOExeption, Application, FXMLLoader, dan Library Scene seperti Parent, Scene dan Stage.

// IOExeption digunakan untuk menunjukkan kesalahan I/O. Kelas ini dapat terjadi saat terjadi kesalahan saat membaca atau menulis data dari atau ke perangkat I/O.

// javafx.application.Application adalah kelas dasar untuk aplikasi JavaFX. Kelas ini menyediakan metode start(), yang digunakan untuk memulai aplikasi.

// FXMLLoader adalah kelas yang digunakan untuk memuat file FXML.

// Sedangkan untuk library scene, Parent adalah kelas dasar untuk semua node tampilan dalam scene JavaFX, Scene adalah kelas yang mewakili tampilan aplikasi JavaFX, dan Stage adalah kelas yang mewakili jendela aplikasi JavaFX.

// public class App extends Application adalah Kelas App yang mewarisi kelas Application, yang merupakan kelas dasar untuk aplikasi JavaFX.


public class App extends Application {

    // @Override - public void start(Stage primaryStage) throws Exception merupakan metode utama yang dipanggil saat aplikasi dimulai. Parameter primaryStage mewakili jendela utama aplikasi.
    @Override
    public void start(Stage primaryStage) throws Exception {

        // Kami menggunakan parent dengan nama variabel root akan menyimpan referensi ke root node dari scene pertama.
        Parent root;
        try {
            // Kami menggunakan FXMLLoader untuk memuat file FXML.
            root = FXMLLoader.load(getClass().getResource("/login/Login.fxml"));
            // Kami menggunakan Scene untuk mewakili tampilan aplikasi JavaFX.
            Scene scene = new Scene(root);
            
            // Menggunakan setTitle untuk mengatur judul jendela aplikasi.
            primaryStage.setTitle("Indo Java - Powered by ODGJ");
            // Menggunakan setScene untuk mengatur tampilan jendela aplikasi.
            primaryStage.setScene(scene);
            // Menggunakan show untuk menampilkan jendela aplikasi.
            primaryStage.show();

        // Jika terjadi kesalahan saat memuat file FXML, blok catch akan menampilkan kesalahan tersebut.
        } catch (IOException e) {

            // printStackTrace digunakan untuk menampilkan stack trace dari kesalahan yang terjadi.
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        // Kami menggunakan launch untuk memulai aplikasi JavaFX.
        launch(args);
    }
}
