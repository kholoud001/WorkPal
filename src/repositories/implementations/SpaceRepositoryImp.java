package repositories.implementations;

import entities.Space;
import repositories.interfaces.SpaceRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class SpaceRepositoryImp implements SpaceRepository {

    Connection connection;
    UserRepositoryImp userRepository;
    HashMap<Integer, Space> spaces;


    public SpaceRepositoryImp(Connection connection, UserRepositoryImp userRepository) {
        this.connection = connection;
        this.userRepository = userRepository;
        this.spaces = new HashMap<>();
    }

    public void addSpace(Space space) throws SQLException {
        String query = "INSERT INTO spaces (name, location, description, type, size, availability, equipment, policy, userid) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        // Ensure the statement can return generated keys
        try (PreparedStatement ps = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, space.getName());
            ps.setString(2, space.getLocation());
            ps.setString(3, space.getDescription());

            ps.setString(4, space.getType().name());

            ps.setInt(5, space.getSize());

            // Set availability (boolean)
            ps.setBoolean(6, space.isAvailability());

            ps.setString(7, space.getEquipment());
            ps.setString(8, space.getPolicy());
            ps.setInt(9, space.getUserId());

            int rowsAffected = ps.executeUpdate();

            if (rowsAffected > 0) {
                try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        space.setId(generatedKeys.getInt(1)); // Set the generated ID in the space object
                    }
                }
            }

            spaces.put(space.getId(), space);
        }
    }

}
