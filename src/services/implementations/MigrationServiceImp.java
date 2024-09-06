package services.implementations;

import config.DatabaseConfig;
import services.interfaces.MigrationServiceInterface;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class MigrationServiceImp implements MigrationServiceInterface {

    public void migrateUsersTable() throws SQLException {
        Connection connection = DatabaseConfig.getInstance().getConnection();
        String createTableSQL = "CREATE TABLE IF NOT EXISTS users (" +
                "id SERIAL PRIMARY KEY, " +
                "name VARCHAR(255) NOT NULL, " +
                "password VARCHAR(255) NOT NULL, " +
                "email VARCHAR(255) NOT NULL UNIQUE, " +
                "phone VARCHAR(20), " +
                "address TEXT, " +
                "profilePicture TEXT" +
                ");";

        try (Statement statement = connection.createStatement()) {
            statement.execute(createTableSQL);
            System.out.println("Table 'users' created successfully.");
        }

        DatabaseConfig.getInstance().closeConnection();


    }
}
