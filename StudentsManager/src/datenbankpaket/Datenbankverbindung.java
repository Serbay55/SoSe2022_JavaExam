package datenbankpaket;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Datenbankverbindung {
    private static final String VERB = "jdbc:sqlite:studmanagersqlite";

    public static Connection getVerbindung() throws SQLException{
        try{
            Class.forName("org.sqlite.JDBC");
            return DriverManager.getConnection(VERB);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static Connection connect;
    static {
        try {
            connect = getVerbindung();
            System.out.println("successfull");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void runSQL(String s) throws SQLException {
        connect.createStatement().execute(s);
    }
    public static ResultSet runSQLQuery(String x) throws SQLException {
        return connect.createStatement().executeQuery(x);
    }


}
