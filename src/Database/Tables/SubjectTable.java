package Database.Tables;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import Database.Common.DatabaseException;
import Database.Common.DatabaseInterface;
import Database.Common.DatabaseLogger;
import Database.Models.Subject;

public class SubjectTable implements DatabaseInterface<Subject> {
    private Connection connection;

    public SubjectTable(Connection conn) {
        this.connection = conn;
    }

    private static final String GET_ALL_QUERY = "SELECT * FROM Subject";
    private static final String GET_BY_ID_QUERY = "SELECT * FROM Subject WHERE id=?";
    private static final String INSERT_QUERY = "INSERT INTO Subject (user_id, subject_name) VALUES (?,?)";
    private static final String DELETE_QUERY = "DELETE FROM Subject WHERE id=?";


    @Override
    public List<Subject> getAll() {
        List<Subject> subjects = new ArrayList<>();
        try {
            PreparedStatement ps = connection.prepareStatement(GET_ALL_QUERY);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Subject subject = mapResultSetToSubject(rs);
                subjects.add(subject);
            }
        } catch (SQLException e) {
            String errorMessage = "Error fetching the Subjects";
            DatabaseLogger.logError(errorMessage, e);
            throw new DatabaseException(errorMessage, e);
        }
        return subjects;
    }

    @Override 
    public Subject getById(int id) {
        Subject subject = null;
        try {
            PreparedStatement ps = connection.prepareStatement(GET_BY_ID_QUERY);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                subject = mapResultSetToSubject(rs);
            }
        } catch (SQLException e) {
            String errorMessage = "Error fetching Subject by ID " + id;
            DatabaseLogger.logError(errorMessage, e);
            throw new DatabaseException(errorMessage, e);
        }
        return subject;
    }

    @Override
    public void insert(Subject subject) {
        try {
            PreparedStatement ps = connection.prepareStatement(INSERT_QUERY);
            ps.setInt(1, subject.getUser_id());
            ps.setString(2, subject.getSubject_name());
            ps.executeUpdate();
            DatabaseLogger.logInfo("Subject inserted successfully");
        } catch (SQLException e) {
            String errorMessage = "Error inserting Subject";
            DatabaseLogger.logError(errorMessage, e);
            throw new DatabaseException(errorMessage, e);
        }
    }

    @Override
    public void delete(int id) {
        try {
            PreparedStatement ps = connection.prepareStatement(DELETE_QUERY);
            ps.setInt(1, id);
            ps.executeUpdate();
            DatabaseLogger.logInfo("Subject deleted successfully");
        } catch (SQLException e) {
            String errorMessage = "Error deleting Subject";
            DatabaseLogger.logError(errorMessage, e);
            throw new DatabaseException(errorMessage, e);
        }
    }

    @Override
    public void update(int id, String column, Object value) {
        String query = "UPDATE Subject SET " + column + " = ? WHERE id = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setObject(1, value);
            ps.setInt(2, id);
            ps.executeUpdate();
            DatabaseLogger.logInfo("Subject " + id + " updated successfully");
        } catch (SQLException e) {
            String errorMessage = "Error updating Subject " + id;
            DatabaseLogger.logError(errorMessage, e);
            throw new DatabaseException(errorMessage, e);
        }
    }

    private Subject mapResultSetToSubject(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        int user_id = rs.getInt("user_id");
        String subject_name = rs.getString("subject_name");
        return new Subject(id, user_id, subject_name);
    }
}
