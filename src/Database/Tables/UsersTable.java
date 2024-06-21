package Database.Tables;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import Database.Common.DatabaseException;
import Database.Common.DatabaseInterface;
import Database.Common.DatabaseLogger;
import Database.Models.User;

public class UsersTable implements DatabaseInterface<User> {

    private Connection conn;

    public UsersTable(Connection connection) {
        this.conn = connection;
    }

    private static final String GET_ALL_QUERY = "SELECT * FROM Users";
    private static final String GET_BY_ID_QUERY = "SELECT * FROM Users WHERE id=?";
    private static final String INSERT_QUERY = "INSERT INTO Users (name, email, password, created_at) VALUES (?, ?, ?, ?)";
    private static final String DELETE_QUERY = "DELETE FROM Users WHERE id=?";

    @Override
    public List<User> getAll() {
        List<User> users = new ArrayList<>();
        try (PreparedStatement ps = conn.prepareStatement(GET_ALL_QUERY); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                User user = mapResultSetToUser(rs);
                users.add(user);
            }
        } catch (SQLException e) {
            String errorMessage = "Error fetching all users";
            DatabaseLogger.logError(errorMessage, e);
            throw new DatabaseException(errorMessage, e);
        }
        return users;
    }

    @Override
    public User getById(int id) {
        User user = null;
        try (PreparedStatement ps = conn.prepareStatement(GET_BY_ID_QUERY)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    user = mapResultSetToUser(rs);
                }
            }
        } catch (SQLException e) {
            String errorMessage = "Error fetching user by ID: " + id;
            DatabaseLogger.logError(errorMessage, e);
            throw new DatabaseException(errorMessage, e);
        }
        return user;
    }

    @Override
    public void insert(User user) {
        try (PreparedStatement ps = conn.prepareStatement(INSERT_QUERY)) {
            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPassword());
            ps.setTimestamp(4, user.getCreated_at());
            ps.executeUpdate();
            DatabaseLogger.logInfo("User inserted successfully");
        } catch (SQLException e) {
            String errorMessage = "Error inserting user: " + user.getName();
            DatabaseLogger.logError(errorMessage, e);
            throw new DatabaseException(errorMessage, e);
        }
    }

    @Override
    public void delete(int id) {
        try (PreparedStatement ps = conn.prepareStatement(DELETE_QUERY)) {
            ps.setInt(1, id);
            int deletedRows = ps.executeUpdate();
            if (deletedRows > 0) {
                DatabaseLogger.logInfo("User with ID: " + id + " deleted successfully");
            } else {
                DatabaseLogger.logInfo("No user found with ID: " + id);
            }
        } catch (SQLException e) {
            String errorMessage = "Error deleting user with ID: " + id;
            DatabaseLogger.logError(errorMessage, e);
            throw new DatabaseException(errorMessage, e);
        }
    }

    @Override
    public void update(int id,String column, Object value) {
        String query="UPDATE Users SET"+column+" =? WHERE id = ?";
       try{
        PreparedStatement ps=conn.prepareStatement(query);
        ps.setObject(1,value);
        ps.setInt(2,id);
        ps.executeUpdate();
        DatabaseLogger.logInfo("User"+id +"updated successfully");
       }catch(SQLException e){
           String errorMessage="Error updating user"+id;
           DatabaseLogger.logError(errorMessage,e);
           throw new DatabaseException(errorMessage,e);
       }
    }

    private User mapResultSetToUser(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String name = rs.getString("name");
        String email = rs.getString("email");
        String password = rs.getString("password");
        Timestamp createdAt = rs.getTimestamp("created_at");
        return new User(id, name, email, password, createdAt);
    }
}
