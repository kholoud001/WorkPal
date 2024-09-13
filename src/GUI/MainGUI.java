package GUI;

import config.DatabaseConfig;
import entities.User;
import repositories.implementations.*;
import repositories.interfaces.*;
import services.implementations.*;
import services.interfaces.*;

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
    SpaceRepository spaceRepository = new SpaceRepositoryImp(connection,userRepository);
    AdditionalServiceRepository additionalServiceRepository = new AdditionalServiceRepositoryImp(connection,userRepository);
    ReservationRepository resRepository = new ReservationRepositoryImp(connection);

    // Instantiate a concrete implementation of UserService
    UserService userService = new UserServiceImp(connection, userRepository, roleRepository);
    SpaceService scpaceService = new SpaceServiceImp(userRepository,spaceRepository,connection);
    AdditionalSService additionalSService =new AdditionalSServiceImp(additionalServiceRepository);
    ReservationService reservationService = new ReservationServiceimp(resRepository,userService);




    private AuthGUI authGUI;
    private Scanner scanner;

    public MainGUI(AuthGUI authGUI, Scanner scanner) throws SQLException {
        this.authGUI = authGUI;
        this.scanner = scanner;
    }
    // Start the application
    public void start() throws SQLException {
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
                        } else if (user.getRole().getId()==3) {
                            ManagerGUI managerGUI = new ManagerGUI(userService,user,scpaceService,additionalSService,scanner);
                            managerGUI.displayMenuManager();
                        }
                        else if (user.getRole().getId()==2) {
                            MemberGUI memberGUI= new MemberGUI(reservationService,scpaceService,user,scanner);
                            memberGUI.displayMemberMenu();
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
                            } else if (user.getRole().getId()==3) {
                                ManagerGUI managerGUI = new ManagerGUI(userService,user,scpaceService,additionalSService,scanner);
                                managerGUI.displayMenuManager();
                            }
                            else if (user.getRole().getId()==2) {
                                MemberGUI memberGUI= new MemberGUI(reservationService,scpaceService,user,scanner);
                                memberGUI.displayMemberMenu();
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
