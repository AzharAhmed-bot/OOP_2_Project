package GUI.common;


import javax.swing.*;

import Database.Models.AcademicGoal;
import Database.Models.EnergyLevel;
import Database.Models.Subject;
import Database.Models.User;
import java.sql.*;
import java.time.LocalTime;
import java.util.ArrayList;

public class AuthenticationController {
    private AuthenticationService authService;


    public AuthenticationController() {
        authService = new AuthenticationService();
        
    }

    public User handleSignUp(String name, String email, String password, Timestamp createdAt) throws SQLException {
        User newUser = authService.newUser(name, email, password, createdAt);
        if (newUser != null) {
            System.out.println("User created: " + newUser.getName());
            return newUser;
        }
        return null;
    }
    

    public User handleLogin(String email, String password) {
        try {
            User user = authService.authenticate(email, password);
            if (user != null) {
                System.out.println("Login successful, welcome " + user.getName());
                return user;
                // Navigate to main application page or perform other actions
            } else {
                System.out.println("Login failed");
                return null;
            }
        } catch (SQLException e ) {
            JOptionPane.showMessageDialog(null, "Error authenticating user: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            return null;
        }
    }

    public AcademicGoal handleSaveGoals(int userId,String goal_description,Date target_date,int priority_level,String status){
        AcademicGoal newAcademicGoal=authService.newAcademicGoal(userId, goal_description, target_date, priority_level, status);
        if (newAcademicGoal != null) {
            return newAcademicGoal;
        }
        return null;
    }
    public Subject handleSaveSubject(String subjectName, int userId,int priority_level){
        Subject newSubject=authService.newSubject(subjectName, userId,priority_level);
        ;
        if (newSubject != null) {
            newSubject.print();
            return newSubject;
        }
        return null;
    }

    public EnergyLevel handleSaveEnergyLevel(int userId,LocalTime timeOfDay,int energyRating){
        EnergyLevel newEnergyLevel=authService.newEnergyLevel(userId, timeOfDay, energyRating);
        if(newEnergyLevel !=null){
            return newEnergyLevel;
        }
        return null;
    }

    public int getTotalGoalsPerUser(int userId){
        int totalGoalCount=authService.getTotalGoalsPerUser(userId);
        if(totalGoalCount != 0){
            return totalGoalCount;
        }
        return 0;
    }

    public int getTotalSubjectsPerUser(int userId){
        int totalSubjectCount=authService.getTotalSubjectsByUserId(userId);
        if(totalSubjectCount != 0){
            return totalSubjectCount;
        }
        return 0;
    }
    
    public ArrayList<Object> getTotalScheduleTimePerUser(int userId){
        ArrayList<Object> totalTScheduleTime=authService.getTotalTimeScedulePerUser(userId);
        if(totalTScheduleTime.size()>0){
            return totalTScheduleTime;
        }else{
            return null;
        }
    }

    public ArrayList<Object> getAllSubjectsPerUser(int userId){
        ArrayList<Object> allSubjectName=authService.getAllSubjectsPerUser(userId);
        if(allSubjectName.size()>0){
            return allSubjectName;
        }else{
            return null;
        }


    }
  
    
}
