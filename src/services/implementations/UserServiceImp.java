package services.implementations;

import entities.Role;
import entities.User;
import repositories.interfaces.RoleRepository;
import repositories.interfaces.UserRepository;
import services.interfaces.UserService;
import utils.PasswordUtils;

import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.util.Optional;

public class UserServiceImp  implements UserService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private Connection connection;

    public UserServiceImp(Connection connection, UserRepository userRepository, RoleRepository roleRepository) {
        this.connection = connection;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }


    public boolean register(String name, String password, String email, String phone, String address, String profilePicture, Integer roleId) throws NoSuchAlgorithmException {
        if (userRepository.findByEmail(email).isPresent()) {
            System.out.println("User with this email already exists.");
            return false;
        }

        // Fetch role from database
        Optional<Role> roleOptional = roleRepository.findById(roleId);
        if (roleOptional.isEmpty()) {
            System.out.println("Role not found.");
            return false;
        }

        // Create and save new user
        User user = new User(0, name, password, email, phone, address, profilePicture, roleOptional.get());
        userRepository.addUser(user);
        //System.out.println("User registered successfully!");
        return true;
    }




    public Optional<User> login(String email, String password) {

        Optional<User> userOptional = userRepository.findByEmail(email);

        if (userOptional.isPresent()) {
            User user = userOptional.get();

            try {
                String hashedPassword = PasswordUtils.hashPassword(password);

                if (user.getPassword().equals(hashedPassword)) {
                    return Optional.of(user);
                } else {
                    System.out.println("Incorrect password.");
                }
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
                System.out.println("Error hashing password: " + e.getMessage());
            }
        } else {
            System.out.println("User with this email does not exist.");
        }

        return Optional.empty();
    }

}
