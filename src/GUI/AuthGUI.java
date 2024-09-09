package GUI;

import config.DatabaseConfig;
import entities.Role;
import repositories.implementations.RoleRepositoryImp;
import repositories.implementations.UserRepositoryImp;
import repositories.interfaces.RoleRepository;
import repositories.interfaces.UserRepository;
import services.implementations.UserServiceImp;
import services.interfaces.RoleService;
import services.interfaces.UserService;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

public class AuthGUI {

    private UserService userService;
    private RoleService roleService;
    private  Scanner scanner;


    public AuthGUI(RoleService roleService, UserService userService) {
        this.roleService = roleService;
        this.userService = userService;
    }

    public void register() {
        Scanner scanner = new Scanner(System.in);

        // Get user input
        System.out.println("Enter Name:");
        String name = scanner.nextLine();

        System.out.println("Enter Password:");
        String password = scanner.nextLine();

        // Validate Email
        String email;
        while (true) {
            System.out.println("Enter Email:");
            email = scanner.nextLine();
            if (isValidEmail(email)) {
                break;
            } else {
                System.out.println("Invalid email format. Please try again.");
            }
        }

        // Validate Phone Number
        String phone;
        while (true) {
            System.out.println("Enter Phone (9 digits):");
            phone = scanner.nextLine();
            if (isValidPhone(phone)) {
                break;
            } else {
                System.out.println("Invalid phone number format. It should be exactly 9 digits.");
            }
        }

        System.out.println("Enter Address:");
        String address = scanner.nextLine();

        System.out.println("Enter Profile Picture URL:");
        String profilePicture = scanner.nextLine();

        // Get roles from database
        List<Role> roles = roleService.getAllRoles();
        if (roles.isEmpty()) {
            System.out.println("No roles available in the system.");
            return;
        }

        System.out.println("Select a role:");
        int i = 1;
        for (Role role : roles) {
            System.out.println(i + ". " + role.getRoleName());
            i++;
        }

        int roleChoice = scanner.nextInt();
        if (roleChoice < 1 || roleChoice > roles.size()) {
            System.out.println("Invalid choice.");
            return;
        }

        Role selectedRole = roles.get(roleChoice - 1);

        // Call service to register the user
        boolean success = userService.register(name, password, email, phone, address, profilePicture, selectedRole.getId());

        if (success) {
            System.out.println("User registered successfully!");
        } else {
            System.out.println("Registration failed. Please try again.");
        }
    }


    public void  testRegister() throws SQLException {
        DatabaseConfig dbConfig = DatabaseConfig.getInstance();
        try (Connection connection = dbConfig.getConnection()) {
            // Instantiate repositories
            RoleRepository roleRepository = new RoleRepositoryImp(connection);
            UserRepository userRepository = new UserRepositoryImp(connection, roleRepository);

            // Instantiate a concrete implementation of UserService
            UserService userService = new UserServiceImp(connection, userRepository, roleRepository);

            // Register Admin
            //userService.register("Admin", "admin@gmail.com", "admin@gmail.com", "555-555-5555", "123 Main St", "john.png", 1);
            userService.register("kiki kiki", "password123", "kiki@example.com", "555-555-5555", "123 Main St", "john.png", 2);


        } catch (SQLException e) {
            e.printStackTrace();  // Handle SQL exceptions
        }
    }


    // Email validation method using regex
    private boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        return Pattern.matches(emailRegex, email);
    }

    // Phone validation method for exactly 9 digits
    private boolean isValidPhone(String phone) {
        String phoneRegex = "\\d{9}";  // Phone must be exactly 9 digits
        return Pattern.matches(phoneRegex, phone);
    }
}
