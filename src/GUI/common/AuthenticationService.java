package GUI.common;

import Database.Connection.DatabaseConnection;
import Database.Models.User;
import Database.Tables.UsersTable;

import java.sql.*;
public class AuthenticationService {
    private Connection connection;
    private UsersTable usersTable;
    
    public AuthenticationService(){
        try{
        connection=DatabaseConnection.getConnection();
        usersTable=new UsersTable(connection);
        }catch(SQLException E){
            E.printStackTrace();
        }
        
    }
    public User authenticate(String email, String password) throws SQLException {
        Connection connection = DatabaseConnection.getConnection();
        UsersTable usersTable = new UsersTable(connection);
        return usersTable.Login(email, password);
    }

    public User newUser(String name,String email,String password,Timestamp created_at){
        User newUser=new User(name, email, password, created_at);
        usersTable.insert(newUser);
        return newUser;
    }
}
