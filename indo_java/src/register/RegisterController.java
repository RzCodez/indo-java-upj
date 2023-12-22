package register;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Session.Session;
import User.User;
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

// Ini adalah class RegisterController
public class RegisterController {
  	// Class PasswordField dari javafx scene layout
    @FXML
    PasswordField passwordInput;
  	// Class TextField dari javafx scene layout
    @FXML
    TextField usernameInput;
  	// Class induk dari semua kelas tampilan grafis di JavaFX
    Parent root;
  	// Class ini berisi semua objek tampilan grafis yang akan ditampilkan di layar.
    Scene scene;
  	// Class untuk menampilkan scene
    Stage stage;
  	// Variable untuk URL Database, harus bersifat private dan const karena tidak akan berubah
    private static final String DB_URL = "jdbc:mysql://localhost:3306/db_barang";
  	// Ini adalah method submit yang digunakan untuk melakukan submit terhadap akun yang ingin didaftar. Dalam method tersebut, ada beberapa pengecekan simpel terhadap inputan username dan password. 
    public void submit(ActionEvent event) throws IOException, SQLException {
      	// Variable string untuk username dan password yang digunakan untuk mendapatkan sebuah value inputan dari user.
        String username = usernameInput.getText();
        String password = passwordInput.getText();
      	// Menggunakan class User dengan variable registeredUser dan memanggil method registerUser untuk menampung username dan password
        User registeredUser = registerUser(username, password);
      	// Melakukan pengecekan dengan if statement, jika registeredUser terpenuhi parameternya, maka akan menjalankan 
        if (registeredUser != null) {
            System.out.println("Registration successful!");
            System.out.println("Welcome, " + registeredUser.getUsername() + "!");
            // Set username ke dalam session
            Session.setUsername(registeredUser.getUsername());
            // Load file FXML untuk tampilan Dashboard
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/dashboard/Dashboard.fxml"));
            // Load root node dari file FXML
            root = loader.load();
            // Set stage sebagai parent node dari root
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            // Buat scene baru dengan root node sebagai konten
            scene = new Scene(root);
            // Set scene baru ke stage
            stage.setScene(scene);
            // Tampilkan stage
            stage.show();
        } else {
            // Tampilkan alert error jika registrasi gagal
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Registrasi gagal");
            alert.setHeaderText(null);
            alert.setContentText("Gagal untuk mendaftar user!");
            alert.showAndWait();
        }
    }
  	// Method registerUser digunakan untuk melakukan registrasi user baru ke database.
    private User registerUser(String username, String password) throws SQLException {
        // Try-with-resources untuk memastikan koneksi database ditutup dengan benar
        try (Connection connection = DriverManager.getConnection(DB_URL, "root", "")) {
            // Buat prepared statement untuk select user berdasarkan username
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM `tabel_user` WHERE username = ?");
            // Set parameter username ke prepared statement
            statement.setString(1, username);
            // Execute query
            ResultSet resultSet = statement.executeQuery();
            // Cek apakah username sudah ada
            if (resultSet.next()) {
                // Tampilkan alert error jika username sudah terdaftar
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Registrasi gagal!");
                alert.setHeaderText(null);
                alert.setContentText("Username sudah terdaftar!");
                alert.showAndWait();
                return null;
            } else {
                // Buat prepared statement untuk insert user baru
                statement = connection.prepareStatement("INSERT INTO `tabel_user` (username, password, role) VALUES (?, ?, ?)");
                // Set parameter username dan password ke prepared statement
                statement.setString(1, username);
                statement.setString(2, password);
                statement.setString(3, "user");
                // Execute update query
                int rowsAffected = statement.executeUpdate();
                // Cek apakah insert berhasil
                if (rowsAffected > 0) {
                    // Buat objek User baru
                    User user = new User();
                    // Set username dan password ke objek User
                    user.setUsername(username);
                    user.setPassword(password);
                  	// Return variable user jika berhasil didaftarkan
                    return user;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
      	// return null jika didaftarkan
        return null;
    }
}