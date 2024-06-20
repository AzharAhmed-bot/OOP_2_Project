import Database.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        try {
            Connection connection = DatabaseConnection.getConnection();
            UsersTable users = new UsersTable(connection);

            List<User> userList = users.getAll();
            for (User user : userList) {
                System.out.println("ID: " + user.getId() + ", Name: " + user.getName() + ", Email: " + user.getEmail() + ", Created At: " + user.getCreatedAt());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
