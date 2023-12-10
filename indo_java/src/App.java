import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

public class App extends Application {

    private Parent root;
    private Scene scene;

    // private Stage stage;
    @Override
    public void start(Stage stage) throws IOException {
        try {
            root = FXMLLoader.load(getClass().getResource("/login/Login.fxml"));
            // root = FXMLLoader.load(getClass().getResource("/admin/dashboard/AdminDashboard.fxml"));
            scene = new Scene(root);
            stage.setTitle("Indo Java - Powered by ODGJ");
            stage.setScene(scene);
            stage.show();

            stage.setOnCloseRequest(e -> {
                e.consume();
                Logout(stage);
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void Logout(Stage stage) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Logout");
        alert.setHeaderText("Kamu akan Logout!");
        alert.setContentText("Apakah anda ingin keluar?");
        if (alert.showAndWait().get() == ButtonType.OK) {
            System.out.println("Berhasil logout");
            stage.close();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}