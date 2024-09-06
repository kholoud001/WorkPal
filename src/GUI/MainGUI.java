package GUI;

import config.DatabaseConfig;

import java.sql.Connection;
import java.sql.SQLException;

public class MainGUI {

    // test connection to database
    public void testConnection() {
        DatabaseConfig dbConfig = new DatabaseConfig();
        try {
            Connection conn = dbConfig.getConnection();
            System.out.println("Connected to database");

            dbConfig.closeConnection(conn);
            System.out.println("Database closed");
        } catch (
                SQLException e) {
            System.out.println(e.getMessage());
        }
    }


}
