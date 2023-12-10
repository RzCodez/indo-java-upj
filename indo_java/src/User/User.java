package User;

public class User {
    private static String username;
    private static String password;
    private static String role;
    public String getUsername() {
        return username;
    }

    public static void setUsername(String username) {
        User.username = username;
    }

    public void setPassword(String password) {
        User.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setRole(String role) {
        User.role = role;
    }

    public String getRole() {
        return role;
    }
}
