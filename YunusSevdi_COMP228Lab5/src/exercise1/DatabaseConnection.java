package exercise1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    
    private static final String URL = "jdbc:oracle:thin:@199.212.26.208:1521:SQLD";
    private static final String URL1 = "jdbc:oracle:thin:@oracle1.centennialcollege.ca:1521:SQLD";
                                       
    private static final String USERNAME = "COMP228_F24_tham_24"; 
    private static final String PASSWORD = "password"; 

    public static Connection getConnection() throws SQLException {
        try {
            
            DriverManager.registerDriver(new oracle.jdbc.OracleDriver());

           
            Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);

           
            conn.setAutoCommit(true);

            System.out.println("Database connection established successfully.");
            return conn;
        } catch (SQLException ex) {
            System.err.println("Error connecting to the database: " + ex.getMessage());
            throw ex; 
        }
    }
}
