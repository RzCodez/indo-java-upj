import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        Parent root;
        try {

            root = FXMLLoader.load(getClass().getResource("/dashboard/Dashboard.fxml"));

            if (root == null) {
                System.err.println("FXML file is null. Please check the file path and naming.");
                return;
            }

            Scene scene = new Scene(root);
            
            primaryStage.setTitle("Indo Java - Powered by ODGJ");

            primaryStage.setScene(scene);

            primaryStage.show();

        } catch (IOException e) {

            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
