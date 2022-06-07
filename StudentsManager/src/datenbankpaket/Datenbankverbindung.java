package datenbankpaket;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Datenbankverbindung {
    private static final String VERB = "jdbc:sqlite:studmanagersqlite";

    public static Connection getVerbindung() throws SQLException{
        try{
            Class.forName("org.sqlite.JDBC");
            return DriverManager.getConnection(VERB);
        } catch (ClassNotFoundException e){
            e.printStackTrace();
        }
        return null;
    }
}
