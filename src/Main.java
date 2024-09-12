import GUI.AdminGUI;
import GUI.AuthGUI;
import GUI.MainGUI;
import config.DatabaseConfig;
import entities.User;
import repositories.implementations.RoleRepositoryImp;
import repositories.implementations.UserRepositoryImp;
import repositories.interfaces.RoleRepository;
import repositories.interfaces.UserRepository;
import services.implementations.RoleServiceImp;
import services.implementations.UserServiceImp;
import services.interfaces.RoleService;
import services.interfaces.UserService;

import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Optional;
import java.util.Scanner;

public class Main {



    public static void main(String[] args) throws SQLException, NoSuchAlgorithmException {

        Scanner scanner = new Scanner(System.in);
        //DB connnection
        DatabaseConfig dbConfig = DatabaseConfig.getInstance();
        Connection connection = dbConfig.getConnection();
        // Instantiate User and Role repositories
        RoleRepository roleRepository = new RoleRepositoryImp(connection);
        UserRepository userRepository = new UserRepositoryImp(connection, roleRepository);

        // Instantiate a concrete implementation of UserService
        UserService userService = new UserServiceImp(connection, userRepository, roleRepository);
        RoleService roleService = new RoleServiceImp(roleRepository, connection);

        AuthGUI authGUI = new AuthGUI(roleService, userService,scanner);


        MainGUI gui = new MainGUI(authGUI,scanner);
//        gui.testConnection();
        //gui.MigrateEntities();
        //gui.addUserforAdmin();
        //gui.start();





















    }
}
