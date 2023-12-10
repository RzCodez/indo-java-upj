package register;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Session.Session;
import User.User;
import dashboard.DashboardController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class RegisterController {
    @FXML
    PasswordField passwordInput;

    @FXML
    TextField usernameInput;

    Parent root;
    Scene scene;
    Stage stage;

    private static final String DB_URL = "jdbc:mysql://localhost:3306/db_barang";
    
    public void submit(ActionEvent event) throws IOException, SQLException {
        String username = usernameInput.getText();
        String password = passwordInput.getText();

        User registeredUser = registerUser(username, password);

        if (registeredUser != null) {
            System.out.println("Registration successful!");
            System.out.println("Welcome, " + registeredUser.getUsername() + "!");
            Session.setUsername(registeredUser.getUsername());

            FXMLLoader loader;
            loader = new FXMLLoader(getClass().getResource("/dashboard/Dashboard.fxml"));
            root = loader.load();
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Registration Failed");
            alert.setHeaderText(null);
            alert.setContentText("Failed to register the user");
            alert.showAndWait();
        }
    }

    private User registerUser(String username, String password) throws SQLException {
        try (Connection connection = DriverManager.getConnection(DB_URL, "root", "")) {
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO `tabel_user` (username, password, role) VALUES (?, ?, ?)");

            statement.setString(1, username);
            statement.setString(2, password);
            statement.setString(3, "user");

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                User user = new User();
                user.setUsername(username);
                user.setPassword(password);
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
