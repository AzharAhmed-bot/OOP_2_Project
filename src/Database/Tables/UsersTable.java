package Database.Tables;

import Database.Common.*;
import Database.Models.User;

import java.sql.*;

public class UsersTable extends BaseTable<User> {
    public UsersTable(Connection connection) {
        super(connection);
    }

    @Override
    protected String getTableName() {
        return "Users";
    }

    @Override
    protected User mapResultSetToEntity(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String name = rs.getString("name");
        String email = rs.getString("email");
        String password = rs.getString("password");
        Timestamp createdAt = rs.getTimestamp("created_at");
        return new User(id, name, email, password, createdAt);
    }

    public void insert(User user) {
        String query = "INSERT INTO Users (name, email, password, created_at) VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPassword());
            ps.setTimestamp(4, user.getCreated_at());
            ps.executeUpdate();
            DatabaseLogger.logInfo("User inserted successfully");
        } catch (SQLException e) {
            String errorMessage = "Error inserting User";
            DatabaseLogger.logError(errorMessage, e);
            throw new DatabaseException(errorMessage, e);
        }
    }
}
