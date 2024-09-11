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
                "description VARCHAR(255) NOT NULL, " +
                "type VARCHAR(255) NOT NULL, " +
                "size INT NOT NULL, " +
                "availability BOOLEAN, " +
                "equipment TEXT, " +
                "policy TEXT, " +
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
                "type VARCHAR(255) NOT NULL, " +
                "price DECIMAL(10, 2), " +
                "spaceId INT REFERENCES spaces(id)" +
                ");";

        try (Statement statement = connection.createStatement()) {
            statement.execute(createTableSQL);
            System.out.println("Table 'events' created successfully.");
        }

        DatabaseConfig.getInstance().closeConnection();
    }

    public void migrateFeedbackTable() throws SQLException {
        Connection connection = DatabaseConfig.getInstance().getConnection();
        String createTableSQL = "CREATE TABLE IF NOT EXISTS feedbacks (" +
                "id SERIAL PRIMARY KEY, " +
                "comment VARCHAR(255) NOT NULL, " +
                "spaceId INT REFERENCES spaces(id)," +
                "userId INT REFERENCES users(id)" +
                ");";

        try (Statement statement = connection.createStatement()) {
            statement.execute(createTableSQL);
            System.out.println("Table 'feedbacks' created successfully.");
        }

        DatabaseConfig.getInstance().closeConnection();
    }

    public void migrateAdditionalServiceable() throws SQLException {
        Connection connection = DatabaseConfig.getInstance().getConnection();
        String createTableSQL = "CREATE TABLE IF NOT EXISTS additional_services (" +
                "id SERIAL PRIMARY KEY, " +
                "name VARCHAR(255) NOT NULL, " +
                "quantity INT NOT NULL, " +
                "price DECIMAL(10, 2), " +
                "userId INT REFERENCES users(id)" +
                ");";

        try (Statement statement = connection.createStatement()) {
            statement.execute(createTableSQL);
            System.out.println("Table 'additional services' created successfully.");
        }

        DatabaseConfig.getInstance().closeConnection();
    }

    public void migrateReservationsTable() throws SQLException {
        Connection connection = DatabaseConfig.getInstance().getConnection();
        String createTableSQL = "CREATE TABLE IF NOT EXISTS reservations (" +
                "id SERIAL PRIMARY KEY, " +
                "start_date DATE NOT NULL, " +
                "end_date DATE NOT NULL, " +
                "status BOOLEAN, " +
                "userId INT REFERENCES users(id)," +
                "spaceId INT REFERENCES spaces(id)," +
                "additional_serviceId INT REFERENCES additional_services(id)" +
                ");";

        try (Statement statement = connection.createStatement()) {
            statement.execute(createTableSQL);
            System.out.println("Table 'reservations' created successfully.");
        }

        DatabaseConfig.getInstance().closeConnection();
    }

    public void migrateFavoritesTable() throws SQLException {
        Connection connection = DatabaseConfig.getInstance().getConnection();
        String createTableSQL = "CREATE TABLE IF NOT EXISTS favorites (" +
                "id SERIAL PRIMARY KEY, " +
                "userId INT REFERENCES users(id)," +
                "spaceId INT REFERENCES spaces(id)" +
                ");";

        try (Statement statement = connection.createStatement()) {
            statement.execute(createTableSQL);
            System.out.println("Table 'favorites' created successfully.");
        }

        DatabaseConfig.getInstance().closeConnection();
    }

    public void migrateSubsTable() throws SQLException {
        Connection connection = DatabaseConfig.getInstance().getConnection();
        String createTableSQL = "CREATE TABLE IF NOT EXISTS subscriptions (" +
                "id SERIAL PRIMARY KEY, " +
                "plan Text NOT NULL, " +
                "start_date DATE NOT NULL, " +
                "end_date DATE NOT NULL, " +
                "status BOOLEAN, " +
                "invoice TEXT NOT NULL, " +
                "price DECIMAL(10, 2), " +
                "userId INT REFERENCES users(id)" +
                ");";

        try (Statement statement = connection.createStatement()) {
            statement.execute(createTableSQL);
            System.out.println("Table 'subscriptions' created successfully.");
        }

        DatabaseConfig.getInstance().closeConnection();
    }











}
