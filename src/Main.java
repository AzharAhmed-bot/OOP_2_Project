import Database.Connection.DatabaseConnection;
import Database.Models.AcademicGoal;
import Database.Tables.AcademicGoalsTable;
import Database.Tables.UsersTable;

import java.sql.Connection;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        try {
            Connection connection = DatabaseConnection.getConnection();
            UsersTable users=new UsersTable(connection);
            AcademicGoalsTable academicGoalsTable = new AcademicGoalsTable(connection);

            AcademicGoal academicGoal=academicGoalsTable.getById(1);
            System.out.println(academicGoal.getUser_id());


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
