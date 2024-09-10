package repositories.implementations;

import entities.Role;
import entities.User;
import repositories.interfaces.RoleRepository;
import repositories.interfaces.UserRepository;
import utils.PasswordUtils;

import java.security.NoSuchAlgorithmException;
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
    public User addUser(User user) throws NoSuchAlgorithmException {
        String query = "INSERT INTO users (name, password, email, phone, address, profilePicture, roleid) VALUES (?, ?, ?, ?, ?, ?, ?)";
        String hashedPassword = PasswordUtils.hashPassword(user.getPassword());

        try(PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, user.getName());
            statement.setString(2, hashedPassword);
            statement.setString(3, user.getEmail());
            statement.setString(4, user.getPhone());
            statement.setString(5, user.getAddress());
            statement.setString(6, user.getProfilePicture());
            statement.setInt(7, user.getRole().getId());

            int rowsAffected = statement.executeUpdate();

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



    public User updateUser(User user) {
        String query = "UPDATE users SET name = ?, password = ?, email = ?, phone = ?, address = ?, profilePicture = ?, roleid = ? WHERE id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            // Set parameters
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setString(4, user.getPhone());
            preparedStatement.setString(5, user.getAddress());
            preparedStatement.setString(6, user.getProfilePicture());
            preparedStatement.setInt(7, user.getRole().getId());
            preparedStatement.setInt(8, user.getId());

            // Execute update
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                //System.out.println("User updated successfully.");
                return user;
            } else {
                //System.out.println("User update failed.");
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error updating user: " + e.getMessage());
            return null;
        }
    }


    public User deleteUser(User user) {
        String query = "DELETE FROM users WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            // Set the ID of the user to be deleted
            preparedStatement.setInt(1, user.getId());

            // Execute the update to delete the user
            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                //System.out.println("User deleted successfully!");
                return user;
            } else {
                //System.out.println("User deletion failed. No user found with the given ID.");
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error occurred while deleting user.");
            return null;
        }
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

                    User user = new User(
                            resultSet.getInt("id"),
                            resultSet.getString("name"),
                            resultSet.getString("password"),
                            resultSet.getString("email"),
                            resultSet.getString("phone"),
                            resultSet.getString("address"),
                            resultSet.getString("profilePicture"),
                            new Role(resultSet.getInt("roleid"))
                    );
                    return Optional.of(user);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }


    public Optional<User> findById(int userId) {
        String query = "SELECT * FROM users WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, userId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {

                    User user = new User(
                            resultSet.getInt("id"),
                            resultSet.getString("name"),
                            resultSet.getString("password"),
                            resultSet.getString("email"),
                            resultSet.getString("phone"),
                            resultSet.getString("address"),
                            resultSet.getString("profilePicture"),
                            new Role(resultSet.getInt("roleid"))
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
