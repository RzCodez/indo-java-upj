```java

public class LoginController {

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

    // URL database
    private static final String DB_URL = "jdbc:mysql://localhost:3306/db_barang";

    // Metode ini dipanggil saat tombol "Submit" ditekan
    public void submit(ActionEvent event) throws IOException, SQLException {
        // Mendapatkan nilai username dan password dari input pengguna
        String username = usernameInput.getText();
        String password = passwordInput.getText();

        // Memanggil metode authenticateUser untuk memeriksa kredensial
        User authenticatedUser = authenticateUser(username, password);

        // Jika kredensial valid, lanjutkan ke dashboard yang sesuai
        if (authenticatedUser != null) {
            System.out.println("Login berhasil!");
            System.out.println("Selamat datang, " + authenticatedUser.getUsername() + "!");
            // Menyimpan nama pengguna dalam sesi
            Session.setUsername(authenticatedUser.getUsername());

            // Memuat tampilan berdasarkan peran pengguna (admin atau pengguna biasa)
            FXMLLoader loader;
            if (authenticatedUser.getRole().equals("admin")) {
                loader = new FXMLLoader(getClass().getResource("/admin/dashboard/AdminDashboard.fxml"));
                root = loader.load();
            } else {
                loader = new FXMLLoader(getClass().getResource("/dashboard/Dashboard.fxml"));
                root = loader.load();
            }

            // Menyiapkan tampilan dan menampilkannya
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } else {
            // Menampilkan pesan kesalahan jika kredensial tidak valid
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Gagal Login");
            alert.setHeaderText(null);
            alert.setContentText("Username atau password salah");
            alert.showAndWait();
        }
    }

    // Metode untuk mengautentikasi pengguna dengan username dan password
    private User authenticateUser(String username, String password) throws SQLException {
        try (Connection connection = DriverManager.getConnection(DB_URL, "root", "")) {
            // Membuat pernyataan SQL untuk mencari pengguna dengan username dan password
            // tertentu
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT username, password, role FROM `tabel_user` WHERE BINARY username = ? AND password = ?");

            // Menetapkan parameter pada pernyataan SQL
            statement.setString(1, username);
            statement.setString(2, password);

            // Mengeksekusi pernyataan SQL dan mendapatkan hasil
            try (ResultSet resultSet = statement.executeQuery()) {
                // Jika ada hasil, membuat objek User dan mengembalikannya
                if (resultSet.next()) {
                    User user = new User();
                    user.setUsername(resultSet.getString("username"));
                    user.setPassword(resultSet.getString("password"));
                    user.setRole(resultSet.getString("role"));
                    return user;
                }
            }
        } catch (SQLException e) {
            // Menangani kesalahan SQL dengan mencetak stack trace
            e.printStackTrace();
        }
        // Mengembalikan null jika autentikasi gagal
        return null;
    }

    // Metode ini dipanggil saat tombol "Register" ditekan untuk pindah ke halaman
    // pendaftaran
    public void registerCta(ActionEvent event) throws IOException {
        // Memuat tampilan pendaftaran dan menampilkan halaman tersebut
        root = FXMLLoader.load(getClass().getResource("/register/Register.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}


```