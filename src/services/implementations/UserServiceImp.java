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


    /**
     *        Authentification
     */
    public boolean register(String name, String password, String email, String phone, String address, String profilePicture, Integer roleId) throws NoSuchAlgorithmException {
        if (userRepository.findByEmail(email).isPresent()) {
            System.out.println("User with this email already exists.");
            return false;
        }

        Optional<Role> roleOptional = roleRepository.findById(roleId);
        if (roleOptional.isEmpty()) {
            System.out.println("Role not found.");
            return false;
        }

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

    /**
     *        ADMIN Part
     */
    public Optional<User> addMemberOrManager(User currentUser, String name, String password, String email, String phone, String address, String profilePicture, Integer roleId) throws NoSuchAlgorithmException {

        if (currentUser.getRole().getId()!=1) {
            System.out.println("\n Only admins can add new members or managers.");
            return Optional.empty();
        }

        String hashedPassword;
        try {
            hashedPassword = PasswordUtils.hashPassword(password);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            System.out.println("Error hashing password.");
            return Optional.empty();
        }

        User newUser = new User();
        newUser.setName(name);
        newUser.setPassword(hashedPassword);
        newUser.setEmail(email);
        newUser.setPhone(phone);
        newUser.setAddress(address);
        newUser.setProfilePicture(profilePicture);

        Optional<Role> roleOptional = roleRepository.findById(roleId);
        if (!roleOptional.isPresent()) {
            System.out.println("Invalid role ID.");
            return Optional.empty();
        }

        Role role = roleOptional.get();

        if (!role.getRoleName().equalsIgnoreCase("member") && !role.getRoleName().equalsIgnoreCase("manager")) {
            System.out.println("Only 'member' or 'manager' roles can be assigned.");
            return Optional.empty();
        }

        newUser.setRole(role);

        User addedUser = userRepository.addUser(newUser);
        return Optional.ofNullable(addedUser);
    }

    public Optional<User> updateUser(User currentUser, Integer userId, String name, String password, String email, String phone, String address, String profilePicture, Integer roleId) throws NoSuchAlgorithmException {
        // Only admins can update users
        if (currentUser.getRole().getId() != 1) {
            System.out.println("\n Only admins can update users.");
            return Optional.empty();
        }

        // Retrieve the existing user by userId
        Optional<User> userOptional = userRepository.findById(userId);
        if (!userOptional.isPresent()) {
            System.out.println("User not found.");
            return Optional.empty();
        }

        User userToUpdate = userOptional.get();

        if (name != null && !name.isEmpty()) {
            userToUpdate.setName(name);
        }

        if (password != null && !password.isEmpty()) {
            try {
                String hashedPassword = PasswordUtils.hashPassword(password);
                userToUpdate.setPassword(hashedPassword);
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
                System.out.println("Error hashing password.");
                return Optional.empty();
            }
        }

        if (email != null && !email.isEmpty()) {
            userToUpdate.setEmail(email);
        }

        if (phone != null && !phone.isEmpty()) {
            userToUpdate.setPhone(phone);
        }

        if (address != null && !address.isEmpty()) {
            userToUpdate.setAddress(address);
        }

        if (profilePicture != null && !profilePicture.isEmpty()) {
            userToUpdate.setProfilePicture(profilePicture);
        }

        if (roleId != null) {
            Optional<Role> roleOptional = roleRepository.findById(roleId);
            if (!roleOptional.isPresent()) {
                System.out.println("Invalid role ID.");
                return Optional.empty();
            }

            Role role = roleOptional.get();
            if (!role.getRoleName().equalsIgnoreCase("member") && !role.getRoleName().equalsIgnoreCase("manager")) {
                System.out.println("Only 'member' or 'manager' roles can be assigned.");
                return Optional.empty();
            }

            userToUpdate.setRole(role);
        }

        User updatedUser = userRepository.updateUser(userToUpdate);
        return Optional.ofNullable(updatedUser);
    }

    public Optional<User> deleteUser(User currentUser, Integer userId) {
        // Only admins can delete users
        if (currentUser.getRole().getId() != 1) {
            System.out.println("\n Only admins can delete users.");
            return Optional.empty();
        }

        // Retrieve the user by userId
        Optional<User> userOptional = userRepository.findById(userId);
        if (!userOptional.isPresent()) {
            System.out.println("User not found.");
            return Optional.empty();
        }

        User userToDelete = userOptional.get();

        // Call the repository method to delete the user
        User deletedUser = userRepository.deleteUser(userToDelete);
        if (deletedUser != null) {
            //System.out.println("User deleted successfully: " + deletedUser.getName());
            return Optional.of(deletedUser);
        } else {
            //System.out.println("Failed to delete the user.");
            return Optional.empty();
        }
    }





}
