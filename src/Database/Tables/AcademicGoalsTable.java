package Database.Tables;

import Database.Models.AcademicGoal;
import Database.Common.*;
import java.sql.*;
import java.util.ArrayList;



public class AcademicGoalsTable extends BaseTable<AcademicGoal> {
    private static final String TABLE_NAME = "AcademicGoals";
    private static final String INSERT_QUERY = "INSERT INTO " + TABLE_NAME + " (user_id, goal_description, target_date, priority_level, status) VALUES (?,?,?,?,?)";


    public AcademicGoalsTable(Connection connection) {
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
     * Maps a ResultSet to an AcademicGoal object.
     *
     * @param  rs   the ResultSet to be mapped
     * @return      the mapped AcademicGoal object
     * @throws SQLException if a database access error occurs
     */
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

    /**
     * Inserts an academic goal into the database.
     *
     * @param  academicGoal  the academic goal to be inserted
     * @throws DatabaseException  if there is an error inserting the academic goal
     */
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

    public int getTotalGoalsByUserId(int userId) {
        String query = "SELECT COUNT(*) FROM " + TABLE_NAME + " WHERE user_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            String errorMessage = "Error fetching total goals for user ID " + userId + " from " + TABLE_NAME;
            DatabaseLogger.logError(errorMessage, e);
            throw new DatabaseException(errorMessage, e);
        }
        return 0;
    }

    public ArrayList<AcademicGoal> getAllAcademicGoalsPerUser(int userId){
        ArrayList<AcademicGoal> allgoals=new ArrayList<>();
        String query="SELECT goal_description,target_date,priority_level,status FROM "+TABLE_NAME+" WHERE user_id = ?";
        try(PreparedStatement ps=connection.prepareStatement(query)){
            ps.setInt(1, userId);
            try(ResultSet rs=ps.executeQuery()){
                while(rs.next()){
                    String goal_description=rs.getString("goal_description");
                    Date target_date=rs.getDate("target_date");
                    int priority_level=rs.getInt("priority_level");
                    String status=rs.getString("status");
                    AcademicGoal academicGoal=new AcademicGoal(userId,goal_description,target_date,priority_level,status);
                    allgoals.add(academicGoal);
                }
            }
        }catch (SQLException e) {
            String errorMessage = "Error fetching total time schedule for user ID " + userId + " from EnergyLevel";
            DatabaseLogger.logError(errorMessage, e);
            throw new DatabaseException(errorMessage, e);
        }
        return allgoals;
    }
}
