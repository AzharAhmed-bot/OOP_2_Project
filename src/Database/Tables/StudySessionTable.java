package Database.Tables;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import Database.Common.DatabaseException;
import Database.Common.DatabaseInterface;
import Database.Common.DatabaseLogger;
import Database.Models.StudySession;

public class StudySessionTable implements DatabaseInterface<StudySession> {
    private Connection connection;

    public StudySessionTable(Connection conn) {
        this.connection = conn;
    }

    private static final String GET_ALL_QUERY = "SELECT * FROM StudySession";
    private static final String GET_BY_ID_QUERY = "SELECT * FROM StudySession WHERE id=?";
    private static final String INSERT_QUERY = "INSERT INTO StudySession (schedule_id, subject_id, user_id, session_date, start_time, end_time, status) VALUES (?,?,?,?,?,?,?)";
    private static final String DELETE_QUERY = "DELETE FROM StudySession WHERE id=?";

    @Override
    public List<StudySession> getAll() {
        List<StudySession> studySessions = new ArrayList<>();
        try {
            PreparedStatement ps = connection.prepareStatement(GET_ALL_QUERY);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                StudySession studySession = mapResultSetToStudySession(rs);
                studySessions.add(studySession);
            }
        } catch (SQLException e) {
            String errorMessage = "Error fetching the Study Sessions";
            DatabaseLogger.logError(errorMessage, e);
            throw new DatabaseException(errorMessage, e);
        }
        return studySessions;
    }

    @Override
    public StudySession getById(int id) {
        StudySession studySession = null;
        try {
            PreparedStatement ps = connection.prepareStatement(GET_BY_ID_QUERY);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                studySession = mapResultSetToStudySession(rs);
            }
        } catch (SQLException e) {
            String errorMessage = "Error fetching Study Session by ID " + id;
            DatabaseLogger.logError(errorMessage, e);
            throw new DatabaseException(errorMessage, e);
        }
        return studySession;
    }

    @Override
    public void insert(StudySession studySession) {
        try {
            PreparedStatement ps = connection.prepareStatement(INSERT_QUERY);
            ps.setInt(1, studySession.getSchedule_id());
            ps.setInt(2, studySession.getSubject_id());
            ps.setInt(3, studySession.getUser_id());
            ps.setDate(4, studySession.getSession_date());
            ps.setTime(5, studySession.getStart_time());
            ps.setTime(6, studySession.getEnd_time());
            ps.setString(7, studySession.getStatus());
            ps.executeUpdate();
            DatabaseLogger.logInfo("Study Session inserted successfully");
        } catch (SQLException e) {
            String errorMessage = "Error inserting Study Session";
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
            DatabaseLogger.logInfo("Study Session deleted successfully");
        } catch (SQLException e) {
            String errorMessage = "Error deleting Study Session";
            DatabaseLogger.logError(errorMessage, e);
            throw new DatabaseException(errorMessage, e);
        }
    }

    @Override
    public void update(int id, String column, Object value) {
        String query = "UPDATE StudySession SET " + column + " = ? WHERE id = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setObject(1, value);
            ps.setInt(2, id);
            ps.executeUpdate();
            DatabaseLogger.logInfo("Study Session " + id + " updated successfully");
        } catch (SQLException e) {
            String errorMessage = "Error updating Study Session " + id;
            DatabaseLogger.logError(errorMessage, e);
            throw new DatabaseException(errorMessage, e);
        }
    }

    private StudySession mapResultSetToStudySession(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        int schedule_id = rs.getInt("schedule_id");
        int subject_id = rs.getInt("subject_id");
        int user_id = rs.getInt("user_id");
        Date session_date = rs.getDate("session_date");
        Time start_time = rs.getTime("start_time");
        Time end_time = rs.getTime("end_time");
        String status = rs.getString("status");
        return new StudySession(id, schedule_id, subject_id, user_id, session_date, start_time, end_time, status);
    }
}
