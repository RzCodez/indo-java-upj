package login;

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
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.Node;

public class LoginController extends Session {

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

        User authenticatedUser = authenticateUser(username, password);

        if (authenticatedUser != null) {

            System.out.println("Login berhasil!");
            System.out.println("Selamat datang, " + authenticatedUser.getUsername() + "!");
            Session.setUsername(authenticatedUser.getUsername());

            FXMLLoader loader;
            if (authenticatedUser.getRole().equals("admin")) {
                loader = new FXMLLoader(getClass().getResource("/admin/dashboard/AdminDashboard.fxml"));
                root = loader.load();
            } else {
                loader = new FXMLLoader(getClass().getResource("/dashboard/Dashboard.fxml"));
                root = loader.load();

                DashboardController dashboardController = loader.getController();
                dashboardController.displayName();
            }

            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Gagal Login");
            alert.setHeaderText(null);
            alert.setContentText("Username atau password salah");
            alert.showAndWait();
        }

    }

    private User authenticateUser(String username, String password) throws SQLException {
        try (Connection connection = DriverManager.getConnection(DB_URL, "root", "")) {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT username, password, role FROM `tabel_user` WHERE BINARY username = ? AND password = ?");

            statement.setString(1, username);
            statement.setString(2, password);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    User user = new User();
                    user.setUsername(resultSet.getString("username"));
                    user.setPassword(resultSet.getString("password"));
                    user.setRole(resultSet.getString("role"));
                    return user;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void registerCta(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/register/Register.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}