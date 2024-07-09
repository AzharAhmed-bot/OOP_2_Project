package GUI.common;

public class Scheduler {
    int userId;
    AuthenticationController authController;

    public Scheduler(int userId){
        authController=new AuthenticationController();
        this.userId=userId;
    }

    public void printSchedule(){
        System.out.println(authController.getTotalScheduleTimePerUser(userId));

    }
    public void printSubject(){
        System.out.println(authController.getAllSubjectsPerUser(userId));
    }
}
