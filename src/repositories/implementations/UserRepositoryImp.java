package repositories.implementations;

import entities.Role;
import entities.User;
import repositories.interfaces.RoleRepository;
import repositories.interfaces.UserRepository;

import java.sql.*;
import java.util.Collection;
import java.util.HashMap;
import java.util.Optional;
import java.util.stream.Collectors;

public class UserRepositoryImp implements UserRepository {
    private Connection connection;
    private RoleRepository roleRepository;
    private HashMap<Integer, User> users;


    public UserRepositoryImp(Connection connection,RoleRepository roleRepository) {
        this.connection = connection;
        this.roleRepository = roleRepository;
        this.users = new HashMap<>();
    }

    public User addUser(User user) {
        String query = "INSERT INTO users (name, password, email, phone, address, profilePicture, roleid) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try(PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, user.getName());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getEmail());
            statement.setString(4, user.getPhone());
            statement.setString(5, user.getAddress());
            statement.setString(6, user.getProfilePicture());
            statement.setInt(7, user.getRole().getId());

            int rowsAffected = statement.executeUpdate();

            // Get the generated key (user ID) if rows were inserted
            if (rowsAffected > 0) {
                try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        user.setId(generatedKeys.getInt(1));  // Set the generated ID
                    }
                }
            }

            users.put(user.getId(), user);

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return user;
    }

    public Optional<User> findByEmail(String email) {

        for (User user : users.values()) {
            if (user.getEmail().equals(email)) {
                return Optional.of(user);
            }
        }
        return Optional.empty();
    }

    public Collection<User> findAll() {
        return users.values();
    }

    public Collection<User> findUserByRole(Role role) {
        return users.values().stream()
                .filter(user -> user.getRole().getId() == role.getId())
                .collect(Collectors.toList());
    }

}
