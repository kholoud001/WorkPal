package repositories.implementations;

import entities.Role;
import repositories.interfaces.RoleRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class RoleRepositoryImp implements RoleRepository {
    private Connection connection;

    public RoleRepositoryImp( Connection connection) {
        this.connection = connection;
    }



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


}
