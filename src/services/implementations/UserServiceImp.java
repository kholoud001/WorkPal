package services.implementations;

import entities.Role;
import entities.User;
import repositories.interfaces.RoleRepository;
import repositories.interfaces.UserRepository;
import services.interfaces.UserService;

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


    public boolean register(String name, String password, String email, String phone, String address, String profilePicture, Integer roleId) {
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
        System.out.println("User registered successfully!");
        return true;
    }


}
