package GUI.common;

import java.awt.Component;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import GUI.Pages.*;


public class Navigator {
    public Navigator(){
        
    }

    public void navigateToLoginPage(Component component){
        System.out.println("Navigating to login page");
        JFrame frame=(JFrame) SwingUtilities.getWindowAncestor(component);
        if(frame != null){
            LoginPage loginPage=new LoginPage();
            frame.setContentPane(loginPage);
            frame.revalidate();
            frame.repaint();
        }else {
            System.err.println("Unable to find JFrame ancestor");
        }
    }

    public void navigateToSignUpPage(Component component){
        System.out.println("Navigating to Sign page");
        JFrame frame=(JFrame) SwingUtilities.getWindowAncestor(component);
        if(frame != null){
            SignUpPage signUpPage=new SignUpPage();
            frame.setContentPane(signUpPage);
            frame.revalidate();
            frame.repaint();
        }else {
            System.err.println("Unable to find JFrame ancestor");
        }
    }

    public void navigateToAcademicGoalPage(Component component,int userId,String userName){
        System.out.println("Navigating to Academic Goal page");
        JFrame frame=(JFrame) SwingUtilities.getWindowAncestor(component);
        if(frame != null){
            AcademicGoalsPage academicGoalPage=new AcademicGoalsPage(userId,userName);
            frame.setContentPane(academicGoalPage);
            frame.revalidate();
            frame.repaint();
        }else {
            System.err.println("Unable to find JFrame ancestor");
        }
    }

    public void navigateToSubjectPage(Component component,int userId, String userName,int goalCount){
        System.out.println("Navigating to Subjects page");
        JFrame frame=(JFrame) SwingUtilities.getWindowAncestor(component);
        if(frame != null){
            SubjectsPage subjectsPage=new SubjectsPage(userId,userName,goalCount);
            frame.setContentPane(subjectsPage);
            frame.revalidate();
            frame.repaint();
        }else {
            System.err.println("Unable to find JFrame ancestor");
        }
    }
    public void navigateToEnergyRatingPage(Component component,int userId, String userName){
        System.out.println("Navigating to Energy Rating page");
        JFrame frame=(JFrame) SwingUtilities.getWindowAncestor(component);
        if(frame!=null){
            EnergyRatingPage energyRatingPage=new EnergyRatingPage(userId,userName);
            frame.setContentPane(energyRatingPage);
            frame.revalidate();
            frame.repaint();
        }else{
            System.err.println("Unable to find JFrame ancestor");
        }
    }

    public void navigateToMainPage(Component component,int userId,String userName){
        System.out.println("Navigating to Main Page");
        JFrame currentFrame=(JFrame) SwingUtilities.getWindowAncestor(component);
        if(currentFrame!=null){
            MainPage mainPage=new MainPage(userId, userName);
            currentFrame.setContentPane(mainPage);
            currentFrame.revalidate();
            currentFrame.repaint();
        }
    }

    public void navigateToMyAcademicGoalsPage(Component component,int userId,String userName){
        System.out.println("Navigating to My Academic GoalsPage Page");
        JFrame currentFrame=(JFrame) SwingUtilities.getWindowAncestor(component);
        if(currentFrame!=null){
            MyAcademicGoalsPage myAcademicGoalsPage=new MyAcademicGoalsPage(userId,userName);
            currentFrame.setContentPane(myAcademicGoalsPage);
            currentFrame.revalidate();
            currentFrame.repaint();
        }
    }
}
