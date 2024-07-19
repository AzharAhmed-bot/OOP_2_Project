package GUI.common;

import Database.Connection.DatabaseConnection;
import Database.Models.AcademicGoal;
import Database.Models.EnergyLevel;
import Database.Models.StudySchedule;
import Database.Models.StudySession;
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
    private StudyScheduleTable studyScheduleTable;
    private StudySessionTable studySessionTable;

    
    public AuthenticationService(){
        try{
        connection=DatabaseConnection.getConnection();
        usersTable=new UsersTable(connection);
        academicGoalsTable=new AcademicGoalsTable(connection);
        subjectTable=new SubjectTable(connection);
        energyLevelTable=new EnergyLevelTable(connection);
        studyScheduleTable=new StudyScheduleTable(connection);
        studySessionTable=new StudySessionTable(connection);
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

    public StudySchedule newSchedule(int userId,Timestamp created_at, Timestamp updated_at ){
        StudySchedule newSchedule=new StudySchedule(userId, created_at, updated_at);
        studyScheduleTable.insert(newSchedule);
        return newSchedule;
    }

    public StudySession newStudySession(int schedule_id,int subject_id,int user_id,Date sessionDate,Time start_time,Time end_time,String status){
        StudySession newStudySession=new StudySession(schedule_id, subject_id, user_id, sessionDate, start_time, end_time, status);
        studySessionTable.insert(newStudySession);
        return newStudySession;
    }

    public int getTotalGoalsPerUser(int userId){
        return academicGoalsTable.getTotalGoalsByUserId(userId);
    }
    public int getTotalSubjectsByUserId(int userId){
        return SubjectTable.getTotalSubjectsByUserId(userId);
    }

    public ArrayList<AcademicGoal> getAllAcademicGoalsPerUser(int userId){
        return academicGoalsTable.getAllAcademicGoalsPerUser(userId);
    }
    public ArrayList<Subject> getAllSubjectsPerUser(int userId){
        return  subjectTable.getAllSubjectsByUserId(userId);
    }

    public ArrayList<EnergyLevel> getTotalTimeScedulePerUser(int userId ){
        return energyLevelTable.getTotalTimeScheduleByUserId(userId);
    }

    public ArrayList<StudySchedule> getAllStudySchedules(){
        return studyScheduleTable.getAll();
    }

    public ArrayList<StudySession> getAllStudySessions(){
        return studySessionTable.getAll();
    }

    public ArrayList<StudySession> getStudySessionsByScheduleId(int schedule_id){
        return studySessionTable.getSessionBySchedule(schedule_id);
    }

    public User getAllUserInfoById(int user_id){
        return usersTable.getById(user_id);
    }

    public String  getSubjectNameFromSession(int subject_id){
        Subject mySubject= subjectTable.getById(subject_id);
        return mySubject.getSubject_name();
    }

    public boolean deleteSessionByScheduleId(int schedule_id){

        return studySessionTable.deleteByScheduleId(schedule_id);
    }

    public void updateAcademiGoal(int id, String column,Object value){
        switch(column){
            case "goal_description":
                academicGoalsTable.update(id, "goal_description", value);
                break;
            case "target_date":
                academicGoalsTable.update(id, "target_date", value);
                break;
            case "priority_level":
                academicGoalsTable.update(id, "priority_level", value);
                break;
            case "status":
                academicGoalsTable.update(id, "status", value);
                break;
            default:
                break;
        }
    }

    public void updateUserInfo(int id, String column,Object value){
        switch(column){
            case "name":
                usersTable.update(id, "name", value);
                break;
            case "email":
                usersTable.update(id, "email", value);
                break;
            case "password":
                usersTable.update(id, "password", value);
                break;
            default:
                break;
        }
    }
}
