package Database.Tables;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Database.Models.Subject;
import Database.Common.*;

public class SubjectTable extends BaseTable<Subject> {
    private static final String TABLE_NAME = "Subject";
    private static final String INSERT_QUERY = "INSERT INTO " + TABLE_NAME + " (subject_name, user_id,priority_level) VALUES (?,?,?)";

    public SubjectTable(Connection connection) {
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
     * Maps a ResultSet to a Subject entity.
     *
     * @param  rs   the ResultSet to map
     * @return      a Subject entity mapped from the ResultSet
     * @throws SQLException if an error occurs while retrieving data from the ResultSet
     */
    @Override 
    protected Subject mapResultSetToEntity(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        int user_id = rs.getInt("user_id");
        String subject_name = rs.getString("subject_name");
        int priority_level=rs.getInt("priority_level");
        return new Subject(id, subject_name, user_id,priority_level);
    }

    /**
     * Inserts a Subject object into the database.
     *
     * @param  subject   the Subject object to insert
     * @throws DatabaseException if there is an error inserting the Subject
     */
    public void insert(Subject subject) {
        try {
            PreparedStatement ps = connection.prepareStatement(INSERT_QUERY);
            ps.setString(1, subject.getSubject_name());
            ps.setInt(2, subject.getUser_id());
            ps.setInt(3, subject.getPriority_level());
            ps.executeUpdate();
            DatabaseLogger.logInfo("Subject inserted successfully");
        } catch (SQLException e) {
            String errorMessage = "Error inserting Subject";
            DatabaseLogger.logError(errorMessage, e);
            throw new DatabaseException(errorMessage, e);
        }
    }

    public static int getTotalSubjectsByUserId(int userId) {
        String query = "SELECT COUNT(*) FROM " + TABLE_NAME + " WHERE user_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            String errorMessage = "Error fetching total subjects for user ID " + userId + " from " + TABLE_NAME;
            DatabaseLogger.logError(errorMessage, e);
            throw new DatabaseException(errorMessage, e);
        }
        return 0;
    }

    public ArrayList<Subject> getAllSubjectsByUserId(int userId) {
        ArrayList<Subject> allSubjects = new ArrayList<>();
        String query = "SELECT id,subject_name, priority_level FROM " + TABLE_NAME + " WHERE user_id = ?";
        
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, userId);
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int id=rs.getInt("id");
                    String subjectName = rs.getString("subject_name");
                    int priorityLevel = rs.getInt("priority_level");  
                    Subject subject = new Subject(id,subjectName, userId, priorityLevel);
                    allSubjects.add(subject);
                }
            }
        } catch (SQLException e) {
            String errorMessage = "Error fetching subjects for user ID " + userId + " from " + TABLE_NAME;
            DatabaseLogger.logError(errorMessage, e);
            throw new DatabaseException(errorMessage, e);
        }
        
        return allSubjects;
    }
    
    
}
