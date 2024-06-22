package Database.Tables;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import Database.Common.DatabaseException;
import Database.Common.DatabaseInterface;
import Database.Common.DatabaseLogger;

public abstract class BaseTable<T> implements DatabaseInterface<T> {
    protected Connection connection;

    public BaseTable(Connection connection) {
        this.connection = connection;
    }

    protected abstract String getTableName();
    protected abstract T mapResultSetToEntity(ResultSet rs) throws SQLException;

    @Override
    public List<T> getAll() {
        List<T> entities = new ArrayList<>();
        String query = "SELECT * FROM " + getTableName();
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                T entity = mapResultSetToEntity(rs);
                entities.add(entity);
            }
        } catch (SQLException e) {
            String errorMessage = "Error fetching all records from " + getTableName();
            DatabaseLogger.logError(errorMessage, e);
            throw new DatabaseException(errorMessage, e);
        }
        return entities;
    }

    @Override
    public T getById(int id) {
        T entity = null;
        String query = "SELECT * FROM " + getTableName() + " WHERE id=?";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                entity = mapResultSetToEntity(rs);
            }
        } catch (SQLException e) {
            String errorMessage = "Error fetching record by ID " + id + " from " + getTableName();
            DatabaseLogger.logError(errorMessage, e);
            throw new DatabaseException(errorMessage, e);
        }
        return entity;
    }

    @Override
    public void delete(int id) {
        String query = "DELETE FROM " + getTableName() + " WHERE id=?";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, id);
            ps.executeUpdate();
            DatabaseLogger.logInfo(getTableName() + " record deleted successfully");
        } catch (SQLException e) {
            String errorMessage = "Error deleting record from " + getTableName() + " with ID " + id;
            DatabaseLogger.logError(errorMessage, e);
            throw new DatabaseException(errorMessage, e);
        }
    }

    @Override
    public void update(int id, String column, Object value) {
        String query = "UPDATE " + getTableName() + " SET " + column + " = ? WHERE id = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setObject(1, value);
            ps.setInt(2, id);
            ps.executeUpdate();
            DatabaseLogger.logInfo(getTableName() + " record " + id + " updated successfully");
        } catch (SQLException e) {
            String errorMessage = "Error updating record in " + getTableName() + " with ID " + id;
            DatabaseLogger.logError(errorMessage, e);
            throw new DatabaseException(errorMessage, e);
        }
    }
}
