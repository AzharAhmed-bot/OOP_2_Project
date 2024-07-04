package GUI.common;

import Database.Connection.DatabaseConnection;
import Database.Models.AcademicGoal;
import Database.Models.Subject;
import Database.Models.User;
import Database.Tables.*;


import java.sql.*;
public class AuthenticationService {
    private Connection connection;
    private UsersTable usersTable;
    private AcademicGoalsTable academicGoalsTable;
    private SubjectTable subjectTable;
    
    public AuthenticationService(){
        try{
        connection=DatabaseConnection.getConnection();
        usersTable=new UsersTable(connection);
        academicGoalsTable=new AcademicGoalsTable(connection);
        subjectTable=new SubjectTable(connection);
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

    public AcademicGoal newAcademicGoal(int userId,String goal_description,Date target_date,int priority_level,String status){
        AcademicGoal newAcademicGoal=new AcademicGoal(userId, goal_description, target_date, priority_level, status);
        academicGoalsTable.insert(newAcademicGoal);
        return newAcademicGoal;
    }

    public Subject newSubject(String subjectName,int userId){
        Subject newSubject=new Subject(subjectName, userId);
        subjectTable.insert(newSubject);
        return newSubject;

    }

    public int getTotalGoalsPerUser(int userId){
        return academicGoalsTable.getTotalGoalsByUserId(userId);
    }
    public int getTotalSubjectsByUserId(int userId){
        return SubjectTable.getTotalSubjectsByUserId(userId);
    }
}
