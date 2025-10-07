package application.resources;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnessioneDB {
    private static Connection conn = null;
	
	public static synchronized Connection getConnection() throws SQLException {
    	if(conn == null) {
    		Properties props = new Properties();
            try (InputStream input = ConnessioneDB.class.getResourceAsStream("db.properties")) { 
                if (input == null) throw new RuntimeException("File db.properties non trovato!");
                props.load(input);
                String url = props.getProperty("db.url");
                String username = props.getProperty("db.username");
                String password = props.getProperty("db.password");
                conn = DriverManager.getConnection(url, username, password);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
    	}
    	
    	return conn;
    }
}