package config;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConfig {

    String urlDB = "jdbc:postgresql://localhost:5432/WorkPal";
    String usernameDB = "postgres";
    String passwordDB = "action";


    public  Connection getConnection() throws SQLException {

        try{
            Class.forName("org.postgresql.Driver");
        }catch (ClassNotFoundException e){
            System.out.println(e.getMessage());
        }

        return DriverManager.getConnection(urlDB, usernameDB, passwordDB);
    }

    public void closeConnection(Connection conn) throws SQLException {
        if (conn != null && !conn.isClosed()) {
            conn.close();
        }else {
            System.out.println("Connection null");
        }
    }

}
