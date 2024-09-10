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

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;



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
    RoleService roleService = new RoleServiceImp(roleRepository, connection);
    //GUI for authentification
    AuthGUI authGUI = new AuthGUI(roleService,userService);


    public MainGUI() throws SQLException {
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
    public void MigrateEntities(){
        try {
            migrationService.migrateRolesTable();
            migrationService.migrateUsersTable();

        }catch (Exception e){
            System.out.println(e.getMessage());
            System.out.println("An error occurred while migrating  entities.");
        }
    }


    ///////////// Admin User Management
    public void addUserforAdmin(){
        Optional<User> loggedInUserOptional = authGUI.login();

        // Check if the user is logged in and if they are an admin
        if (loggedInUserOptional.isPresent()) {
            User loggedInUser = loggedInUserOptional.get();
            //System.out.println("Role ID: " + loggedInUser.getRole().getId());

            // Only proceed if the logged-in user is an admin (roleId = 1)
            if (loggedInUser.getRole().getId() == 1) {
                System.out.println("Welcome Admin!");

                AdminGUI adminGUI = new AdminGUI(userService, loggedInUser);

                adminGUI.addNewUser();
            } else {
                System.out.println("You are not authorized to add new users. Only admins can perform this action.");
            }
        } else {
            System.out.println("Login failed. Please try again.");
        }
    }

    /////// start Admin
    public void start(){

    }


}
