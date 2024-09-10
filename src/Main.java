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

public class Main {



    public static void main(String[] args) throws SQLException, NoSuchAlgorithmException {

//        MainGUI gui = new MainGUI();
//        gui.testConnection();
//        gui.MigrateEntities();

        DatabaseConfig dbConfig = DatabaseConfig.getInstance();
        Connection connection = dbConfig.getConnection();
            // Instantiate repositories
            RoleRepository roleRepository = new RoleRepositoryImp(connection);
            UserRepository userRepository = new UserRepositoryImp(connection, roleRepository);

            // Instantiate a concrete implementation of UserService
            UserService userService = new UserServiceImp(connection, userRepository, roleRepository);
            RoleService roleService = new RoleServiceImp(roleRepository, connection);

           AuthGUI authGUI = new AuthGUI(roleService,userService);
           authGUI.register();
           //authGUI.login();










    }
}
