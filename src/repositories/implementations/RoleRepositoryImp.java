package repositories.implementations;

import entities.Role;
import repositories.interfaces.RoleRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RoleRepositoryImp implements RoleRepository {
    private Connection connection;

    public RoleRepositoryImp( Connection connection) {
        this.connection = connection;
    }


    /**
     * Finds a role by its ID from the database.
     *
     * @param roleId the ID of the role to be retrieved
     * @return an `Optional<Role>` containing the role if found, or an empty `Optional` if not
     * @throws RuntimeException if an SQL exception occurs during the query execution
     */
    public Optional<Role> findById(int roleId) {
        String query = "SELECT * FROM roles WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1,roleId);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()) {
                return Optional.of(new Role(resultSet.getInt("id"),resultSet.getString("name")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return Optional.empty();
    }



    /**
     * Finds a role by its name from the database.
     *
     * @param name the name of the role to be retrieved
     * @return an `Optional<Role>` containing the role if found, or an empty `Optional` if not
     * @throws RuntimeException if an SQL exception occurs during the query execution
     */
    public Optional<Role> findByName(String name) {
        String query = "SELECT * FROM roles WHERE name = ?";
        try(PreparedStatement statement=connection.prepareStatement(query)){
            statement.setString(1,name);
            ResultSet resultSet=statement.executeQuery();
            if(resultSet.next()) {
                return Optional.of(new Role(resultSet.getInt("id"),resultSet.getString("name")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    public List<Role> findAll() {
        List<Role> roles = new ArrayList<>();
        String query = "SELECT * FROM roles";

        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Role role = new Role(resultSet.getInt("id"), resultSet.getString("name"));
                roles.add(role);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return roles;
    }

    public List<Role> findRoleApartAdmin() {
        List<Role> roles = new ArrayList<>();
        String query = "SELECT * FROM roles AS r \n" +
                "WHERE r.name = 'MANAGER'\n" +
                "OR r.name='MEMBER'";

        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Role role = new Role(resultSet.getInt("id"), resultSet.getString("name"));
                roles.add(role);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return roles;
    }




}
