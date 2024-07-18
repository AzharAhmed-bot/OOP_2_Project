package Database.Tables;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.sql.Time;


import Database.Common.DatabaseException;
import Database.Common.DatabaseLogger;
import Database.Models.StudySession;

public class StudySessionTable extends BaseTable<StudySession> {
    private static final String TABLE_NAME = "StudySession";
    private static final String INSERT_QUERY = "INSERT INTO " + TABLE_NAME + " (schedule_id, subject_id, user_id, session_date, start_time, end_time, status) VALUES (?,?,?,?,?,?,?)";


    public StudySessionTable(Connection connection) {
        super(connection);
    }

    
    /**
     * Returns the name of the table.
     *
     * @return the name of the table
     */
    @Override
    protected String getTableName() {
        return TABLE_NAME;
    }

    /**
     * Maps a ResultSet to a StudySession entity.
     *
     * @param  rs   the ResultSet to map
     * @return      a StudySession entity
     * @throws SQLException if a database access error occurs
     */
    @Override
    protected StudySession mapResultSetToEntity(ResultSet rs) throws SQLException {
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

    /**
     * Inserts a StudySession object into the database.
     *
     * @param  studySession   the StudySession object to insert
     * @throws DatabaseException if there is an error inserting the StudySession
     */
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

    public boolean deleteByScheduleId(int schedule_id){
        String query = "DELETE FROM " + TABLE_NAME + " WHERE schedule_id = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, schedule_id);
            int rowsAffected=ps.executeUpdate();
            if(rowsAffected>0){
                DatabaseLogger.logInfo("Study Session deleted successfully");
                return true;
            }else{
                return false;
            }   
            
        } catch (SQLException e) {
            String errorMessage = "Error deleting Study Session";
            DatabaseLogger.logError(errorMessage, e);
            throw new DatabaseException(errorMessage, e);
            
        }
        
    }
}
