package Database.Tables;

import java.sql.*;
import Database.Common.*;
import Database.Models.StudySchedule;

public class StudyScheduleTable extends BaseTable<StudySchedule>{

    public StudyScheduleTable(Connection connection){
        super(connection);
    }

    private static final String TABLE_NAME = "StudySchedule";
    private static final String INSERT_QUERY="INSERT INTO StudySchedule (user_id,created_at,updated_at) VALUES (?,?,?)";

    @Override
    protected String getTableName(){
        return TABLE_NAME;
    }
    @Override
    protected StudySchedule mapResultSetToEntity(ResultSet rs) throws SQLException{
        int id=rs.getInt("id");
        int user_id=rs.getInt("user_id");
        Timestamp created_at=rs.getTimestamp("created_at");
        Timestamp updated_at=rs.getTimestamp("updated_at");
        return new StudySchedule(id,user_id, created_at, updated_at);
    }


    public void insert(StudySchedule studySchedule){
        try{
            PreparedStatement ps=connection.prepareStatement(INSERT_QUERY);
            ps.setInt(1,studySchedule.getUser_id());
            ps.setTimestamp(2,studySchedule.getCreated_at());
            ps.setTimestamp(3,studySchedule.getUpdated_at());
            ps.executeUpdate();
            DatabaseLogger.logInfo("Study Schedule inserted successfully");
        }catch(SQLException e){
            String errorMessage="Error inserting Study Schedule";
            DatabaseLogger.logError(errorMessage,e);
            throw new DatabaseException(errorMessage,e);
        }
    }
    

}