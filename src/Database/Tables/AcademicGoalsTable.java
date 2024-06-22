package Database.Tables;

import Database.Models.AcademicGoal;
import Database.Common.*;
import java.sql.*;
import java.util.Date;


public class AcademicGoalsTable extends BaseTable<AcademicGoal> {
    private static final String TABLE_NAME = "AcademicGoals";
    private static final String INSERT_QUERY = "INSERT INTO " + TABLE_NAME + " (user_id, goal_description, target_date, priority_level, status) VALUES (?,?,?,?,?)";


    public AcademicGoalsTable(Connection connection) {
        super(connection);
    }

    @Override
    protected String getTableName() {
        return TABLE_NAME;
    }

    @Override
    protected AcademicGoal mapResultSetToEntity(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        int user_id = rs.getInt("user_id");
        String goal_description = rs.getString("goal_description");
        Date target_date = rs.getDate("target_date");
        int priority_level = rs.getInt("priority_level");
        String status = rs.getString("status");
        return new AcademicGoal(id, user_id, goal_description, (java.sql.Date) target_date, priority_level, status);
    }

    public void insert(AcademicGoal academicGoal) {
        try {
            PreparedStatement ps = connection.prepareStatement(INSERT_QUERY);
            ps.setInt(1, academicGoal.getUser_id());
            ps.setString(2, academicGoal.getGoal_description());
            ps.setDate(3, academicGoal.getTarget_date());
            ps.setInt(4, academicGoal.getPriority_level());
            ps.setString(5, academicGoal.getStatus());
            ps.executeUpdate();
            DatabaseLogger.logInfo("Academic Goal inserted successfully");
        } catch (SQLException e) {
            String errorMessage = "Error inserting Academic Goal";
            DatabaseLogger.logError(errorMessage, e);
            throw new DatabaseException(errorMessage, e);
        }
    }
}
