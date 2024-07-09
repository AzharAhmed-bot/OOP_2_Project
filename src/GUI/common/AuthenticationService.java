package GUI.common;

import Database.Connection.DatabaseConnection;
import Database.Models.AcademicGoal;
import Database.Models.EnergyLevel;
import Database.Models.Subject;
import Database.Models.User;
import Database.Tables.*;
import java.sql.*;
import java.time.LocalTime;
import java.util.ArrayList;


public class AuthenticationService {
    private Connection connection;
    private UsersTable usersTable;
    private AcademicGoalsTable academicGoalsTable;
    private SubjectTable subjectTable;
    private EnergyLevelTable energyLevelTable;
    
    public AuthenticationService(){
        try{
        connection=DatabaseConnection.getConnection();
        usersTable=new UsersTable(connection);
        academicGoalsTable=new AcademicGoalsTable(connection);
        subjectTable=new SubjectTable(connection);
        energyLevelTable=new EnergyLevelTable(connection);
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

    public Subject newSubject(String subjectName,int userId,int priority_level){
        Subject newSubject=new Subject(subjectName, userId,priority_level);
        subjectTable.insert(newSubject);
        newSubject.print();
        return newSubject;

    }
    public EnergyLevel newEnergyLevel(int userId,LocalTime timeOfDay,int energyLevel ){
        EnergyLevel newEnergyLevel=new EnergyLevel(userId, timeOfDay, energyLevel);
        energyLevelTable.insert(newEnergyLevel);
        return newEnergyLevel;
    }

    public int getTotalGoalsPerUser(int userId){
        return academicGoalsTable.getTotalGoalsByUserId(userId);
    }
    public int getTotalSubjectsByUserId(int userId){
        return SubjectTable.getTotalSubjectsByUserId(userId);
    }
    public ArrayList<Object> getAllSubjectsPerUser(int userId){
        return  subjectTable.getAllSubjectsByUserId(userId);
    }

    public ArrayList<Object> getTotalTimeScedulePerUser(int userId ){
        return energyLevelTable.getTotalTimeScheduleByUserId(userId);
    }
}
