package datenbankpaket;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Datenbankverbindung {
		
	
    private static final String VERB = "jdbc:sqlite:login.db";

    public static Connection getVerbindung() throws SQLException{
        try{
            Class.forName("org.sqlite.JDBC");
            return DriverManager.getConnection(VERB);
            
        } catch (SQLException e) {
        	e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
    private static Connection connection;
    
    public synchronized static void runSQL(String query) throws SQLException {
    	connection.createStatement().execute(query);
    }
    public static ResultSet runSQLquery(String query) throws SQLException {
    	return connection.createStatement().executeQuery(query);
    }
    
    static {
    	try {
    		connection = getVerbindung();
    	}
    	catch (RuntimeException e) {
    		e.printStackTrace();
    	} catch (SQLException e) {
			e.printStackTrace();
		}
    
    	
		
	}
}




