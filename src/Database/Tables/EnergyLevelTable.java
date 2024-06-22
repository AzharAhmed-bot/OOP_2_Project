package Database.Tables;

import Database.Models.*;
import Database.Common.*;

import java.sql.*;

public class EnergyLevelTable extends BaseTable<EnergyLevel> {
    public EnergyLevelTable(Connection connection) {
        super(connection);
    }

    @Override
    protected String getTableName() {
        return "EnergyLevels";
    }

    @Override
    protected EnergyLevel mapResultSetToEntity(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        int user_id = rs.getInt("user_id");
        String time_of_day = rs.getString("time_of_day");
        int energy_rating = rs.getInt("energy_rating");
        return new EnergyLevel(id, user_id, time_of_day, energy_rating);
    }

    public void insert(EnergyLevel energyLevel) {
        String query = "INSERT INTO EnergyLevels (user_id, time_of_day, energy_rating) VALUES (?, ?, ?)";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, energyLevel.getUser_id());
            ps.setString(2, energyLevel.getTime_of_day());
            ps.setInt(3, energyLevel.getEnergy_rating());
            ps.executeUpdate();
            DatabaseLogger.logInfo("Energy Level inserted successfully");
        } catch (SQLException e) {
            String errorMessage = "Error inserting Energy Level";
            DatabaseLogger.logError(errorMessage, e);
            throw new DatabaseException(errorMessage, e);
        }
    }
}
