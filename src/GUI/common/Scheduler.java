package GUI.common;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import Database.Models.EnergyLevel;
import Database.Models.StudySchedule;
import Database.Models.Subject;

public class Scheduler implements Runnable {
    private int userId;
    private AuthenticationController authController;

    @Override
    public void run() {
        createSession();
    }

    public Scheduler(int userId) {
        authController = new AuthenticationController();
        
        this.userId = userId;
    }

    public void printSchedule() {
        System.out.println(authController.getTotalScheduleTimePerUser(userId));
    }

    public void printSubject() {
        System.out.println(authController.getAllSubjectsPerUser(userId));
    }

    public StudySchedule createSchedule() {
        Timestamp created_at = new Timestamp(System.currentTimeMillis());
        Timestamp updated_at = new Timestamp(System.currentTimeMillis());
        StudySchedule newSchedule = authController.handleSaveSchedule(userId, created_at, updated_at);
        return newSchedule;
    }


    public void createSession() {
        // Retrieve or create the schedule and get its ID
        StudySchedule schedule = createSchedule();
        int schedule_id = schedule.getId();

        ArrayList<EnergyLevel> energyLevels = authController.getTotalScheduleTimePerUser(userId);
        ArrayList<Subject> totalSubjects = authController.getAllSubjectsPerUser(userId);

        // Sorting energyLevels based on energy_rating in descending order
        Collections.sort(energyLevels, Comparator.comparingInt(EnergyLevel::getEnergy_rating).reversed());

        // Sorting totalSubjects based on priority_level in ascending order
        Collections.sort(totalSubjects, Comparator.comparingInt(Subject::getPriority_level));

        for (int i = 0; i < Math.min(energyLevels.size(), totalSubjects.size()); i++) {
            EnergyLevel energyLevel = energyLevels.get(i);
            Subject subject = totalSubjects.get(i);
            Date session_date = Date.valueOf(LocalDate.now());
            int subject_id = subject.getId();
            Time start_time = Time.valueOf(energyLevel.getTime_of_day());
            Time end_time = Time.valueOf(energyLevel.getTime_of_day().plusHours(1).plusMinutes(30)); 
            String status = "New";

            authController.handleSaveStudySession(schedule_id, subject_id, userId, session_date, start_time, end_time, status);

            // Optional: Print details of the session created
            System.out.println("Created session for Subject ID: " + subject.getId() + ", Start Time: " + start_time + ", End Time: " + end_time);
        }
       
    }
}
