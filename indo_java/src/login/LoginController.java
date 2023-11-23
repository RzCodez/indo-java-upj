package login;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {

    @FXML
    private Label labelCheck;

    @FXML
    private PasswordField passwordInput;

    @FXML
    private TextField usernameInput;

    private static final String DB_URL = "jdbc:mysql://localhost:3306/db_barang";

    @FXML
    public void submit(ActionEvent event) {
        String username = usernameInput.getText();
        String password = passwordInput.getText();

        if (authenticateUser(username, password)) {
            labelCheck.setText("Login Success");
            labelCheck.setTextFill(javafx.scene.paint.Color.GREEN);
        } else {
            labelCheck.setText("*Login Failed");
            labelCheck.setTextFill(javafx.scene.paint.Color.RED);
        }
    }

    private boolean authenticateUser(String username, String password) {
        try (Connection connection = DriverManager.getConnection(DB_URL);
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM users WHERE username = ? AND password = ?")) {

            statement.setString(1, username);
            statement.setString(2, password);

            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next(); // Returns true if a matching user is found
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}