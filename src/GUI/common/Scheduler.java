package GUI.common;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import Database.Models.EnergyLevel;
import Database.Models.Subject;

public class Scheduler implements Runnable{
    int userId;
    AuthenticationController authController;


    @Override
    public void run(){
        createSchedule();
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

    public void createSchedule() {
        ArrayList<EnergyLevel> energyLevels = authController.getTotalScheduleTimePerUser(userId);
        ArrayList<Subject> totalSubjects = authController.getAllSubjectsPerUser(userId);

        // Sorting energyLevels based on energy_rating in descending order
        Collections.sort(energyLevels, new Comparator<EnergyLevel>() {
            @Override
            public int compare(EnergyLevel e1, EnergyLevel e2) {
                return Integer.compare(e2.getEnergy_rating(), e2.getEnergy_rating());
            }
        });

        Collections.sort(totalSubjects, new Comparator<Subject>() {
            @Override
            public int compare(Subject s1, Subject s2 ){
                return Integer.compare(s1.getPriority_level(), s2.getPriority_level());
            }
        });

        for(int i=0; i<Math.min(energyLevels.size(),totalSubjects.size());i++){
            EnergyLevel energyLevel=energyLevels.get(i);
            Subject subject=totalSubjects.get(i);
            System.out.println("User id: "+ subject.getUser_id() + ", Time: "+energyLevel.getTime_of_day()+", Subject id: "+subject.getId()+", Subject name: "+subject.getSubject_name() +", Subject Priority: "+subject.getPriority_level()+", Energy Level for subject: "+energyLevel.getEnergy_rating());
        }
    }
    
}
