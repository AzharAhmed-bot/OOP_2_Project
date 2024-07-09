package Database.Tables;

import Database.Models.*;
import Database.Common.*;

import java.sql.*;
import java.time.LocalTime;

public class EnergyLevelTable extends BaseTable<EnergyLevel> {
    public EnergyLevelTable(Connection connection) {
        super(connection);
    }

    /**
     * Gets the table name for EnergyLevels.
     *
     * @return          the table name "EnergyLevels"
     */
    @Override
    protected String getTableName() {
        return "EnergyLevel";
    }

        /**
         * Maps a ResultSet to an EnergyLevel entity.
         *
         * @param  rs  the ResultSet to map
         * @return     the EnergyLevel entity mapped from the ResultSet
         * @throws SQLException if an error occurs while retrieving data from the ResultSet
         */
    @Override
    protected EnergyLevel mapResultSetToEntity(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        int user_id = rs.getInt("user_id");
        Time time_of_day = rs.getTime("time_of_day");
        LocalTime localTimeOfDay = time_of_day.toLocalTime(); // Convert java.sql.Time to LocalTime
        int energy_rating = rs.getInt("energy_rating");
        return new EnergyLevel(id, user_id, localTimeOfDay, energy_rating);
    }
    
    /**
     * Inserts an EnergyLevel object into the EnergyLevels table.
     *
     * @param  energyLevel  the EnergyLevel object to insert
     */
    public void insert(EnergyLevel energyLevel) {
        String query = "INSERT INTO EnergyLevel (user_id, time_of_day, energy_rating) VALUES (?, ?, ?)";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, energyLevel.getUser_id());
            ps.setTime(2, java.sql.Time.valueOf(energyLevel.getTime_of_day()));
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
