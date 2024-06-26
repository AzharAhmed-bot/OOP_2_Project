import GUI.Frame;

public class Main {
    
    /**
     * The main function that serves as the entry point of the program. It establishes a connection to the database,
     * creates a UsersTable object, prompts the user to enter their email and password, and attempts to log the user in.
     * If the login is successful, it prints a welcome message with the user's name. Otherwise, it prints "Login failed".
     *
     * @param  args	an array of command-line arguments
     */
    public static void main(String[] args) {
        // try{
        //     Connection connection=DatabaseConnection.getConnection();
        //     UsersTable usersTable=new UsersTable(connection);
        //     Scanner scanner=new Scanner(System.in);
        //     System.out.print("Enter email: ");
        //     String email=scanner.nextLine();
        //     System.out.print("Enter password: ");
        //     String password=scanner.nextLine();
        //     User user=usersTable.Login(email,password);
        //     if(user!=null){
        //         System.out.println("Login successful,welcome "+ user.getName());
        //     }
        //     else{
        //         System.out.println("Login failed");
        //     }
        //     scanner.close();
            
        // }catch (SQLException e) {
        //     e.printStackTrace();
        // }
        Frame frame=new Frame("StudyBud");
        frame.setVisible(true);
    }
}
