package config;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConfig {

    private static DatabaseConfig instance;
    private Connection connection;
    private String urlDB = "jdbc:postgresql://localhost:5432/WorkPal";
    private String usernameDB = "postgres";
    private String passwordDB = "action";


    private DatabaseConfig() throws SQLException {
        try {
            Class.forName("org.postgresql.Driver");
            this.connection = DriverManager.getConnection(urlDB, usernameDB, passwordDB);
        } catch (ClassNotFoundException ex) {
            System.out.println("Database Connection Creation Failed : " + ex.getMessage());
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public static DatabaseConfig getInstance() throws SQLException {
        if (instance == null) {
            instance = new DatabaseConfig();
        } else if (instance.getConnection().isClosed()) {
            instance = new DatabaseConfig();
        }

        return instance;
    }

    public void closeConnection() throws SQLException {
        connection.close();
    }

}
