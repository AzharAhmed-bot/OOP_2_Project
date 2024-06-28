package Database.Tables;

import Database.Common.*;
import Database.Models.User;

import java.sql.*;

public class UsersTable extends BaseTable<User> {
    public UsersTable(Connection connection) {
        super(connection);
    }


    
    /**
    * Returns the name of the table.
    *
    * @return the name of the table, which is "Users"
    */
    @Override
    protected String getTableName() {
        return "Users";
    }

    /**
    * Maps a ResultSet object to a User object by extracting the values from the ResultSet
    * for the columns "id", "name", "email", and "password".
    *
    * @param  rs  the ResultSet object containing the data to be mapped
    * @return      a User object with the extracted values
    * @throws SQLException if a database access error occurs
    */
    @Override
    protected User mapResultSetToEntity(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String name = rs.getString("name");
        String email = rs.getString("email");
        String password = rs.getString("password");
        Timestamp createdAt = rs.getTimestamp("created_at");
        return new User(id, name, email, password, createdAt);
    }

    /**
     * Inserts a User object into the Users table in the database.
     *
     * @param  user  the User object to be inserted
     * @throws DatabaseException  if there is an error inserting the User
     */
    public void insert(User user) {
        String query = "INSERT INTO Users (name, email, password, created_at) VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPassword());
            ps.setTimestamp(4, user.getCreated_at());
            ps.executeUpdate();
            DatabaseLogger.logInfo("User inserted successfully");
        } catch (SQLException e) {
            String errorMessage = "Error inserting User";
            DatabaseLogger.logError(errorMessage, e);
            throw new DatabaseException(errorMessage, e);
        }
    }

    
    /**
     * Logs in a user by checking if the provided email and password match those in the database.
     *
     * @param  email    the email of the user to log in
     * @param  password the password of the user to log in
     * @return          the User object if the login is successful, null otherwise
     * @throws DatabaseException if there is an error executing the SQL query
     */
    public User Login(String email, String password){
        String query="SELECT * FROM Users WHERE email=? AND password=?";
        try{
            PreparedStatement ps=connection.prepareStatement(query);
            ps.setString(1, email );
            ps.setString(2, password);
            ResultSet rs=ps.executeQuery();
            if(rs.next()){
                return mapResultSetToEntity(rs);
            }
            else{
                return null;
            }
        }catch (SQLException e) {
            String errorMessage = "Error logging in User";
            DatabaseLogger.logError(errorMessage, e);
            throw new DatabaseException(errorMessage, e);
        }
       
    }
}
