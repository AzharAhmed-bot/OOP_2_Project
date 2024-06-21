package Database.Tables;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import Database.Common.DatabaseException;
import Database.Common.DatabaseInterface;
import Database.Common.DatabaseLogger;
import Database.Models.StudySchedule;

public class StudyScheduleTable implements DatabaseInterface<StudySchedule> {
    private Connection connection;

    public StudyScheduleTable(Connection conn) {
        this.connection = conn;
    }

    private static final String GET_ALL_QUERY = "SELECT * FROM StudySchedule";
    private static final String GET_BY_ID_QUERY = "SELECT * FROM StudySchedule WHERE id=?";
    private static final String INSERT_QUERY = "INSERT INTO StudySchedule (user_id, created_at, updated_at) VALUES (?,?,?)";
    private static final String DELETE_QUERY = "DELETE FROM StudySchedule WHERE id=?";

    @Override
    public List<StudySchedule> getAll() {
        List<StudySchedule> studySchedules = new ArrayList<>();
        try {
            PreparedStatement ps = connection.prepareStatement(GET_ALL_QUERY);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                StudySchedule studySchedule = mapResultSetToStudySchedule(rs);
                studySchedules.add(studySchedule);
            }
        } catch (SQLException e) {
            String errorMessage = "Error fetching the Study Schedules";
            DatabaseLogger.logError(errorMessage, e);
            throw new DatabaseException(errorMessage, e);
        }
        return studySchedules;
    }

    @Override
    public StudySchedule getById(int id) {
        StudySchedule studySchedule = null;
        try {
            PreparedStatement ps = connection.prepareStatement(GET_BY_ID_QUERY);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                studySchedule = mapResultSetToStudySchedule(rs);
            }
        } catch (SQLException e) {
            String errorMessage = "Error fetching Study Schedule by ID " + id;
            DatabaseLogger.logError(errorMessage, e);
            throw new DatabaseException(errorMessage, e);
        }
        return studySchedule;
    }

    @Override
    public void insert(StudySchedule studySchedule) {
        try {
            PreparedStatement ps = connection.prepareStatement(INSERT_QUERY);
            ps.setInt(1, studySchedule.getUser_id());
            ps.setTimestamp(2, studySchedule.getCreated_at());
            ps.setTimestamp(3, studySchedule.getUpdated_at());
            ps.executeUpdate();
            DatabaseLogger.logInfo("Study Schedule inserted successfully");
        } catch (SQLException e) {
            String errorMessage = "Error inserting Study Schedule";
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
            DatabaseLogger.logInfo("Study Schedule deleted successfully");
        } catch (SQLException e) {
            String errorMessage = "Error deleting Study Schedule";
            DatabaseLogger.logError(errorMessage, e);
            throw new DatabaseException(errorMessage, e);
        }
    }

    @Override 
    public void update(int id, String column, Object value) {
        String query = "UPDATE StudySchedule SET " + column + " = ? WHERE id = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setObject(1, value);
            ps.setInt(2, id);
            ps.executeUpdate();
            DatabaseLogger.logInfo("Study Schedule " + id + " updated successfully");
        } catch (SQLException e) {
            String errorMessage = "Error updating Study Schedule " + id;
            DatabaseLogger.logError(errorMessage, e);
            throw new DatabaseException(errorMessage, e);
        }
    }
    

    private StudySchedule mapResultSetToStudySchedule(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        int user_id = rs.getInt("user_id");
        Timestamp created_at = rs.getTimestamp("created_at");
        Timestamp updated_at = rs.getTimestamp("updated_at");
        return new StudySchedule(id, user_id, created_at, updated_at);
    }
}
