package GUI;

import config.DatabaseConfig;

import java.sql.Connection;
import java.sql.SQLException;

public class MainGUI {

    // test connection to database
    public void testConnection() {
        try {
            DatabaseConfig dbConfig = DatabaseConfig.getInstance();

            Connection connection = dbConfig.getConnection();

            if (connection != null && connection.isValid(5)) {
                System.out.println("Database connection is valid.");
            } else {
                System.out.println("Database connection is not valid.");
            }

            // dbConfig.closeConnection(connection);

        } catch (SQLException e) {
            System.out.println("An error occurred while testing the connection: " + e.getMessage());
        }
    }


}
