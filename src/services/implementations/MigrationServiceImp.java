package services.implementations;

import config.DatabaseConfig;
import services.interfaces.MigrationServiceInterface;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class MigrationServiceImp implements MigrationServiceInterface {

    public void migrateRolesTable() throws SQLException {
        Connection connection = DatabaseConfig.getInstance().getConnection();
        String createTableSQL = "CREATE TABLE IF NOT EXISTS roles (" +
                "id SERIAL PRIMARY KEY, " +
                "name VARCHAR(255) NOT NULL" +
                ");";

        try (Statement statement = connection.createStatement()) {
            statement.execute(createTableSQL);
            System.out.println("Table 'roles' created successfully.");
        }
        DatabaseConfig.getInstance().closeConnection();

    }

    public void migrateUsersTable() throws SQLException {
        Connection connection = DatabaseConfig.getInstance().getConnection();
        String createTableSQL = "CREATE TABLE IF NOT EXISTS users (" +
                "id SERIAL PRIMARY KEY, " +
                "name VARCHAR(255) NOT NULL, " +
                "password VARCHAR(255) NOT NULL, " +
                "email VARCHAR(255) NOT NULL UNIQUE, " +
                "phone VARCHAR(20), " +
                "address TEXT, " +
                "profilePicture TEXT," +
                "roleId INT REFERENCES roles(id)" +
                ");";

        try (Statement statement = connection.createStatement()) {
            statement.execute(createTableSQL);
            System.out.println("Table 'users' created successfully.");
        }

        DatabaseConfig.getInstance().closeConnection();

    }

    public void migrateSpacesTable() throws SQLException {
        Connection connection = DatabaseConfig.getInstance().getConnection();
        String createTableSQL = "CREATE TABLE IF NOT EXISTS spaces (" +
                "id SERIAL PRIMARY KEY, " +
                "name VARCHAR(255) NOT NULL, " +
                "location VARCHAR(255) NOT NULL, " +
                "type VARCHAR(255) NOT NULL, " +  // Use VARCHAR instead of Enum
                "size INT NOT NULL, " +  // Assuming size in square meters or similar
                "availability BOOLEAN, " +
                "equipment TEXT, " +  // Fixed typo from 'equipement' to 'equipment'
                "policy TEXT, " +  // Changed 'politique' to 'policy'
                "userId INT REFERENCES users(id)" +
                ");";

        try (Statement statement = connection.createStatement()) {
            statement.execute(createTableSQL);
            System.out.println("Table 'spaces' created successfully.");
        }

        DatabaseConfig.getInstance().closeConnection();
    }

    public void migrateEventsTable() throws SQLException {
        Connection connection = DatabaseConfig.getInstance().getConnection();
        String createTableSQL = "CREATE TABLE IF NOT EXISTS events (" +
                "id SERIAL PRIMARY KEY, " +
                "title VARCHAR(255) NOT NULL, " +
                "date DATE NOT NULL, " +
                "time TIME NOT NULL, " +
                "location VARCHAR(255), " +  // Or refer to the 'spaces' table
                "type VARCHAR(255) NOT NULL, " +
                "price DECIMAL(10, 2), " +  // Price field added
                "spaceId INT REFERENCES spaces(id)" +  // Relationship to spaces table
                ");";

        try (Statement statement = connection.createStatement()) {
            statement.execute(createTableSQL);
            System.out.println("Table 'events' created successfully.");
        }

        DatabaseConfig.getInstance().closeConnection();
    }



}
