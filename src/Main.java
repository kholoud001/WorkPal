import GUI.MainGUI;
import config.DatabaseConfig;
import entities.User;
import repositories.implementations.RoleRepositoryImp;
import repositories.implementations.UserRepositoryImp;
import repositories.interfaces.RoleRepository;
import repositories.interfaces.UserRepository;
import services.implementations.UserServiceImp;
import services.interfaces.UserService;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;

public class Main {



    public static void main(String[] args) throws SQLException {

//        MainGUI gui = new MainGUI();
//        gui.testConnection();
//        gui.MigrateEntities();

        DatabaseConfig dbConfig = DatabaseConfig.getInstance();
        try (Connection connection = dbConfig.getConnection()) {
            // Instantiate repositories
            RoleRepository roleRepository = new RoleRepositoryImp(connection);
            UserRepository userRepository = new UserRepositoryImp(connection, roleRepository);

            // Instantiate a concrete implementation of UserService
            UserService userService = new UserServiceImp(connection, userRepository, roleRepository);

            // Register Admin
            //userService.register("Admin", "admin@gmail.com", "admin@gmail.com", "555-555-5555", "123 Main St", "john.png", 1);
            userService.register("lili lim", "password123", "lili@example.com", "555-555-5555", "123 Main St", "john.png", 2);


        } catch (SQLException e) {
            e.printStackTrace();  // Handle SQL exceptions
        }










    }
}
