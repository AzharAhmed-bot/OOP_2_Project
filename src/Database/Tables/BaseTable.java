package Database.Tables;

import java.sql.*;
import java.util.ArrayList;

import Database.Common.DatabaseException;
import Database.Common.DatabaseInterface;
import Database.Common.DatabaseLogger;


public abstract class BaseTable<T> implements DatabaseInterface<T> {
    protected static Connection connection;

    public BaseTable(Connection connection) {
        BaseTable.connection = connection;
    }

    protected abstract String getTableName();
    protected abstract T mapResultSetToEntity(ResultSet rs) throws SQLException;

    /**
     * Retrieves all entities from the database table associated with this instance.
     *
     * @return          a list of entities retrieved from the database
     * @throws DatabaseException if there is an error executing the SQL query
     */
    @Override
    public ArrayList<T> getAll() {
        ArrayList<T> entities = new ArrayList<>();
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

    /**
     * Retrieves an entity from the database based on the given ID.
     *
     * @param  id  the ID of the entity to retrieve
     * @return     the retrieved entity, or null if no entity is found
     * @throws DatabaseException if there is an error executing the SQL query
     */
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

    /**
     * Deletes a record from the database based on the provided ID.
     *
     * @param  id  the ID of the record to delete
     */
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

    /**
     * Updates a record in the database table with the specified ID and column.
     *
     * @param  id        the ID of the record to update
     * @param  column    the column to update
     * @param  value     the new value for the specified column
     * @throws DatabaseException if there is an error updating the record
     */
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
