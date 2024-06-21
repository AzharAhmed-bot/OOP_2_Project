package Database;

import java.sql.*;
import java.sql.Date;
import java.util.*;

public class AcademicGoalsTable implements DatabaseInterface<AcademicGoal> {
    private Connection connection;

    public AcademicGoalsTable(Connection conn){
        this.connection = conn;
    }

    private static final String GET_ALL_QUERY = "SELECT * FROM AcademicGoals";
    private static final String GET_BY_ID_QUERY = "SELECT * FROM AcademicGoals WHERE id=?";
    private static final String INSERT_QUERY = "INSERT INTO AcademicGoals (user_id, goal_description, target_date, priority_level, status) VALUES (?,?,?,?,?)";
    private static final String DELETE_QUERY = "DELETE FROM AcademicGoals WHERE id=?";

    @Override
    public List<AcademicGoal> getAll(){
        List<AcademicGoal> academicGoals = new ArrayList<>();
        try {
            PreparedStatement ps = connection.prepareStatement(GET_ALL_QUERY);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                AcademicGoal academicGoal = mapResultSetToAcademicGoal(rs);
                academicGoals.add(academicGoal);
            }
        } catch (SQLException e) {
            String errorMessage = "Error fetching the Academic Goals";
            DatabaseLogger.logError(errorMessage, e);
            throw new DatabaseException(errorMessage, e);
        }
        return academicGoals;
    }

    @Override
    public AcademicGoal getById(int id){
        AcademicGoal academicGoal = null;
        try {
            PreparedStatement ps = connection.prepareStatement(GET_BY_ID_QUERY);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                academicGoal = mapResultSetToAcademicGoal(rs);
            }
        } catch (SQLException e) {
            String errorMessage = "Error fetching user by ID " + id;
            DatabaseLogger.logError(errorMessage, e);
            throw new DatabaseException(errorMessage, e);
        }
        return academicGoal;
    }

    @Override
    public void insert(AcademicGoal academicGoal){
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

    @Override
    public void delete(int id){
        try {
            PreparedStatement ps = connection.prepareStatement(DELETE_QUERY);
            ps.setInt(1, id);
            ps.executeUpdate();
            DatabaseLogger.logInfo("Academic Goal deleted successfully");
        } catch (SQLException e) {
            String errorMessage = "Error deleting Academic Goal";
            DatabaseLogger.logError(errorMessage, e);
            throw new DatabaseException(errorMessage, e);
        }
    }

    @Override
    public void update(int id, String column, Object value){
        String query = "UPDATE AcademicGoals SET " + column + " = ? WHERE id = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setObject(1, value);
            ps.setInt(2, id);
            ps.executeUpdate();
            DatabaseLogger.logInfo("Academic Goal updated successfully");
        } catch (SQLException e) {
            String errorMessage = "Error updating Academic Goal";
            DatabaseLogger.logError(errorMessage, e);
            throw new DatabaseException(errorMessage, e);
        }
    }

    private AcademicGoal mapResultSetToAcademicGoal(ResultSet rs) throws SQLException{
        int id = rs.getInt("id");
        int user_id = rs.getInt("user_id");
        String goal_description = rs.getString("goal_description");
        Date target_date = rs.getDate("target_date");
        int priority_level = rs.getInt("priority_level");
        String status = rs.getString("status");
        return new AcademicGoal(id, user_id, goal_description, target_date, priority_level, status);
    }
}
