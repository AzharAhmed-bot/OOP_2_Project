package Database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UsersTable implements DatabaseInterface<User> {
    private static final Logger LOGGER = Logger.getLogger(UsersTable.class.getName());
    private Connection conn;

    public UsersTable(Connection connection) {
        this.conn=connection;
    }

    private static final String GET_ALL_QUERY = "SELECT * FROM Users";
    private static final String GET_BY_ID_QUERY = "SELECT * FROM Users WHERE id=?";
    private static final String INSERT_QUERY = "INSERT INTO Users (name, email, password, created_at) VALUES (?, ?, ?, ?)";
    private static final String DELETE_QUERY = "DELETE FROM Users WHERE id=?";
    private static final String UPDATE_QUERY = "UPDATE Users SET name=? WHERE id=?";

    @Override
    public List<User> getAll() {
        List<User> users = new ArrayList<>();
        try (PreparedStatement ps = conn.prepareStatement(GET_ALL_QUERY); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                User user = mapResultSetToUser(rs);
                users.add(user);
            }
        } catch (SQLException e) {
            handleSQLException(e);
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
            handleSQLException(e);
        }
        return user;
    }

    @Override
    public void insert(User user) {
        try (PreparedStatement ps = conn.prepareStatement(INSERT_QUERY)) {
            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPassword());
            ps.setTimestamp(4, user.getCreatedAt());
            ps.executeUpdate();
            LOGGER.info("User inserted successfully");
        } catch (SQLException e) {
            handleSQLException(e);
        }
    }

    @Override
    public void delete(int id) {
        try (PreparedStatement ps = conn.prepareStatement(DELETE_QUERY)) {
            ps.setInt(1, id);
            int deletedRows = ps.executeUpdate();
            if (deletedRows > 0) {
                LOGGER.info("User with ID: " + id + " deleted successfully");
            } else {
                LOGGER.info("No user found with ID: " + id);
            }
        } catch (SQLException e) {
            handleSQLException(e);
        }
    }

    @Override
    public void update(int id, User user) {
        try (PreparedStatement ps = conn.prepareStatement(UPDATE_QUERY)) {
            ps.setString(1, user.getName());
            ps.setInt(2, id);
            int updatedRows = ps.executeUpdate();
            if (updatedRows > 0) {
                LOGGER.info("User with ID: " + id + " updated successfully");
            } else {
                LOGGER.info("No user found with ID: " + id);
            }
        } catch (SQLException e) {
            handleSQLException(e);
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

    private void handleSQLException(SQLException e) {
        LOGGER.log(Level.SEVERE, "Error executing SQL: " + e.getMessage(), e);
    }

   
}
