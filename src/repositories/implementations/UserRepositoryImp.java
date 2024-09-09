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

    /**
     * Adds a new user to the database and stores the user in the `users` collection.
     *
     * @param user the `User` object containing the information to be added
     * @return the `User` object with the updated ID field if the insertion is successful,
     *         otherwise returns the original `User` object with an unchanged ID
     */
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
                        user.setId(generatedKeys.getInt(1));
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

    /**
     * Finds a user by their email address in the users collection.
     *
     * @param email the email address to search for
     * @return an `Optional` containing the `User` object if a match is found,
     *         otherwise an empty `Optional`
     */
    public Optional<User> findByEmail(String email) {
        String query = "SELECT * FROM users WHERE email = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, email);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    // Create a User object from the result set
                    User user = new User(
                            resultSet.getInt("id"),
                            resultSet.getString("name"),
                            resultSet.getString("password"),
                            resultSet.getString("email"),
                            resultSet.getString("phone"),
                            resultSet.getString("address"),
                            resultSet.getString("profilePicture"),
                            new Role(resultSet.getInt("id"),resultSet.getString("name"))
                    );
                    return Optional.of(user);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }

    /**
     * Retrieves all users from the local collection of users.
     *
     * @return a `Collection` of `User` objects representing all users in the system.
     */
    public Collection<User> findAll() {
        return users.values();
    }


    /**
     * Finds all users with a specific role from the local collection of users.
     *
     * @param role the `Role` object representing the role to filter users by
     * @return a `Collection` of `User` objects that have the specified role.
     */
    public Collection<User> findUserByRole(Role role) {
        return users.values().stream()
                .filter(user -> user.getRole().getId() == role.getId())
                .collect(Collectors.toList());
    }

}
