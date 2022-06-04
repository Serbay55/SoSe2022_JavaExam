package defaultpackage;
import java.sql.*;
import java.util.Scanner;

public class Main {
    public static void createdatabase(String filename) {
        String newdatabaseurl = "jdbc:sqlite:C:\\sqlite\\java\\connect\\sqlite-jdbc-3.36.0.3t.jar\\" + filename + ".db";

        try {
            Connection conn = DriverManager.getConnection(newdatabaseurl);
            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();
                System.out.println("The driver name is " + meta.getDriverName());
                System.out.println("A new database has been created.");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println("An error occurred while connecting MySQL database");
        }
    }
    public static void main(String[] args) {
        Scanner io = new Scanner(System.in);

        createdatabase(io.nextLine());
    }
}
