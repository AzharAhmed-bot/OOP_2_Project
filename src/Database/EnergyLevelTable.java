package Database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EnergyLevelTable implements DatabaseInterface<EnergyLevel> {
    private Connection connection;

    public EnergyLevelTable(Connection conn){
        this.connection = conn;
    }

    private static final String GET_ALL_QUERY = "SELECT * FROM EnergyLevels";
    private static final String GET_BY_ID_QUERY = "SELECT * FROM EnergyLevels WHERE id=?";
    private static final String INSERT_QUERY = "INSERT INTO EnergyLevels (user_id, time_of_day, energy_rating) VALUES (?,?,?)";
    private static final String DELETE_QUERY = "DELETE FROM EnergyLevels WHERE id=?";

    @Override
    public List<EnergyLevel> getAll() {
        List<EnergyLevel> energyLevels = new ArrayList<>();
        try {
            PreparedStatement ps = connection.prepareStatement(GET_ALL_QUERY);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                EnergyLevel energyLevel = mapResultSetToEnergyLevel(rs);
                energyLevels.add(energyLevel);
            }
        } catch (SQLException e) {
            String errorMessage = "Error fetching the Energy Levels";
            DatabaseLogger.logError(errorMessage, e);
            throw new DatabaseException(errorMessage, e);
        }
        return energyLevels;
    }

    @Override
    public EnergyLevel getById(int id) {
        EnergyLevel energyLevel = null;
        try {
            PreparedStatement ps = connection.prepareStatement(GET_BY_ID_QUERY);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                energyLevel = mapResultSetToEnergyLevel(rs);
            }
        } catch (SQLException e) {
            String errorMessage = "Error fetching Energy Level by ID " + id;
            DatabaseLogger.logError(errorMessage, e);
            throw new DatabaseException(errorMessage, e);
        }
        return energyLevel;
    }

    @Override
    public void insert(EnergyLevel energyLevel) {
        try {
            PreparedStatement ps = connection.prepareStatement(INSERT_QUERY);
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

    @Override
    public void delete(int id) {
        try {
            PreparedStatement ps = connection.prepareStatement(DELETE_QUERY);
            ps.setInt(1, id);
            ps.executeUpdate();
            DatabaseLogger.logInfo("Energy Level deleted successfully");
        } catch (SQLException e) {
            String errorMessage = "Error deleting Energy Level";
            DatabaseLogger.logError(errorMessage, e);
            throw new DatabaseException(errorMessage, e);
        }
    }

    @Override
    public void update(int id, String column, Object value) {
        String query = "UPDATE EnergyLevels SET " + column + " = ? WHERE id = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setObject(1, value);
            ps.setInt(2, id);
            ps.executeUpdate();
            DatabaseLogger.logInfo("Energy Level " + id + " updated successfully");
        } catch (SQLException e) {
            String errorMessage = "Error updating Energy Level " + id;
            DatabaseLogger.logError(errorMessage, e);
            throw new DatabaseException(errorMessage, e);
        }
    }
    

    private EnergyLevel mapResultSetToEnergyLevel(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        int user_id = rs.getInt("user_id");
        String time_of_day = rs.getString("time_of_day");
        int energy_rating = rs.getInt("energy_rating");
        return new EnergyLevel(id, user_id, time_of_day, energy_rating);
    }
}
