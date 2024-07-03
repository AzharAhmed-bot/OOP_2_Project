package GUI.common;

import java.awt.Component;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import GUI.Pages.LoginPage;
import GUI.Pages.SignUpPage;

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
}
