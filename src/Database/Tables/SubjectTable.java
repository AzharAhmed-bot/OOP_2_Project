package Database.Tables;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Database.Models.Subject;
import Database.Common.*;

public class SubjectTable extends BaseTable<Subject> {
    private static final String TABLE_NAME = "Subject";
    private static final String INSERT_QUERY = "INSERT INTO " + TABLE_NAME + " (subject_name, user_id) VALUES (?,?)";

    public SubjectTable(Connection connection) {
        super(connection);
    }

    @Override
    protected String getTableName() {
        return TABLE_NAME;
    }

    @Override
    protected Subject mapResultSetToEntity(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        int user_id = rs.getInt("user_id");
        String subject_name = rs.getString("subject_name");
        return new Subject(id, subject_name, user_id);
    }

    public void insert(Subject subject) {
        try {
            PreparedStatement ps = connection.prepareStatement(INSERT_QUERY);
            ps.setString(1, subject.getSubject_name());
            ps.setInt(2, subject.getUser_id());
            ps.executeUpdate();
            DatabaseLogger.logInfo("Subject inserted successfully");
        } catch (SQLException e) {
            String errorMessage = "Error inserting Subject";
            DatabaseLogger.logError(errorMessage, e);
            throw new DatabaseException(errorMessage, e);
        }
    }
    
}
