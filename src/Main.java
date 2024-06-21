import Database.*;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        try {
            Connection connection = DatabaseConnection.getConnection();
            UsersTable users=new UsersTable(connection);
            AcademicGoalsTable academicGoalsTable = new AcademicGoalsTable(connection);


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
