package GUI;

import config.DatabaseConfig;
import services.implementations.MigrationServiceImp;
import services.interfaces.MigrationServiceInterface;

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

    public void MigrateEntities(){
        MigrationServiceInterface migrationService = new MigrationServiceImp();
        try {
            migrationService.migrateRolesTable();
            migrationService.migrateUsersTable();

        }catch (Exception e){
            System.out.println(e.getMessage());
            System.out.println("An error occurred while migrating  entities.");
        }
    }


}
