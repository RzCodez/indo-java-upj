package login;

import java.io.IOException;
// import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
// import java.util.ResourceBundle;

import Session.Session;

// import session.Session;

import dashboard.DashboardController;
// import dashboard.transaksi.TransaksiController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.Node;

public class LoginController extends Session {

    @FXML
    private Label labelCheck;

    @FXML
    private PasswordField passwordInput;

    @FXML
    TextField usernameInput;

    private Parent root;
    private Scene scene;
    private Stage stage;

    private static final String DB_URL = "jdbc:mysql://localhost:3306/db_barang";

    public void submit(ActionEvent event) throws IOException, SQLException {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Pesan Error : " + e);
        }

        String username = usernameInput.getText();
        String password = passwordInput.getText();

        if (authenticateUser(username, password)) {
            System.out.println("Login berhasil!");
            System.out.println("Met datang, " + username + "!");
            Session.setUsername(username);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/dashboard/Dashboard.fxml"));
            root = loader.load();
            
            DashboardController dashboardController = loader.getController();
            dashboardController.displayName();
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Gagal Login");
            alert.setHeaderText(null);
            alert.setContentText("username atau password salah");
            alert.showAndWait();
        }
    }

    private Connection connection;
    private boolean authenticateUser(String username, String password) throws SQLException {
        try (Connection connection = DriverManager.getConnection(DB_URL, "root", "");){ 

            PreparedStatement statement = connection.prepareStatement("SELECT * FROM `tabel_user` WHERE BINARY username = ? AND password = ?");

            statement.setString(1, username);
            statement.setString(2, password);

            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        finally {
            if (connection != null) {
                connection.close();
            }
        }
    }

}