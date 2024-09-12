package GUI;

import config.DatabaseConfig;
import entities.User;
import repositories.implementations.RoleRepositoryImp;
import repositories.implementations.UserRepositoryImp;
import repositories.interfaces.RoleRepository;
import repositories.interfaces.UserRepository;
import services.implementations.MigrationServiceImp;
import services.implementations.RoleServiceImp;
import services.implementations.UserServiceImp;
import services.interfaces.MigrationServiceInterface;
import services.interfaces.RoleService;
import services.interfaces.UserService;

import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;
import java.util.Scanner;


public class MainGUI {

    //DB connnection
    DatabaseConfig dbConfig = DatabaseConfig.getInstance();
    Connection connection = dbConfig.getConnection();
    //migration entities
    MigrationServiceInterface migrationService = new MigrationServiceImp();
    // Instantiate User and Role repositories
    RoleRepository roleRepository = new RoleRepositoryImp(connection);
    UserRepository userRepository = new UserRepositoryImp(connection, roleRepository);

    // Instantiate a concrete implementation of UserService
    UserService userService = new UserServiceImp(connection, userRepository, roleRepository);




    private AuthGUI authGUI;
    private Scanner scanner;

    public MainGUI(AuthGUI authGUI, Scanner scanner) throws SQLException {
        this.authGUI = authGUI;
        this.scanner = scanner;
    }
    // Start the application
    public void start() {
        Scanner scanner = new Scanner(System.in);
        Optional<User> loggedInUser = Optional.empty();
        boolean exit = false;

        while (!exit) {
            System.out.println("\n*** Welcome ***");
            System.out.println("1. Login");
            System.out.println("2. Register");
            System.out.println("0. Exit");
            System.out.print("Choose an option: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    loggedInUser = authGUI.login();
                    if (loggedInUser.isPresent()) {
                        User user = loggedInUser.get();
                        if (user.getRole().getId()==1) {
                            AdminGUI adminGUI = new AdminGUI(userService, user,scanner);
                            adminGUI.displayMenuAdmin();
                        } else {
                            System.out.println("You are logged in as a regular user.");
                        }
                    }
                    break;

                case "2":
                    try {
                        authGUI.register();
                        loggedInUser = authGUI.login();
                        if (loggedInUser.isPresent()) {
                            User user = loggedInUser.get();
                            if (user.getRole().getId()==1) {
                                AdminGUI adminGUI = new AdminGUI(userService, user,scanner);
                                adminGUI.displayMenuAdmin();
                            } else {
                                System.out.println("You are logged in as a regular user.");
                            }
                        }
                    } catch (NoSuchAlgorithmException e) {
                        System.out.println("Registration error: " + e.getMessage());
                    }
                    break;

                case "0":
                    System.out.println("Exiting the application...");
                    exit = true;
                    break;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
        scanner.close();
    }



    // test connection to database
    public void testConnection() {
        try {
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

    //Migrate entities
    public void MigrateEntities() {
        try {
//            migrationService.migrateRolesTable();
//            migrationService.migrateUsersTable();
            migrationService.migrateSpacesTable();
//            migrationService.migrateEventsTable();
//            migrationService.migrateFeedbackTable();
//            migrationService.migrateAdditionalServiceable();
//            migrationService.migrateReservationsTable();
//            migrationService.migrateFavoritesTable();
//            migrationService.migrateSubsTable();

        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("An error occurred while migrating  entities.");
        }
    }






}
