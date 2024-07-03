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
}
