package repositories.implementations;

import Enums.TypeSpace;
import entities.Space;
import repositories.interfaces.SpaceRepository;
import repositories.interfaces.UserRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class SpaceRepositoryImp implements SpaceRepository {

    Connection connection;
    UserRepository userRepository;
    HashMap<Integer, Space> spaces;


    public SpaceRepositoryImp(Connection connection, UserRepository userRepository) {
        this.connection = connection;
        this.userRepository = userRepository;
        this.spaces = new HashMap<>();
    }

    public void addSpace(Space space) throws SQLException {
        String query = "INSERT INTO spaces (name, location, description, type, size, availability, equipment, policy, userid) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement ps = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, space.getName());
            ps.setString(2, space.getLocation());
            ps.setString(3, space.getDescription());
            ps.setString(4, space.getType().name());
            ps.setInt(5, space.getSize());
            ps.setBoolean(6, space.isAvailability());
            ps.setString(7, space.getEquipment());
            ps.setString(8, space.getPolicy());
            ps.setInt(9, space.getUserId());

            int rowsAffected = ps.executeUpdate();

            if (rowsAffected > 0) {
                try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        space.setId(generatedKeys.getInt(1));
                    }
                }
            }
            spaces.put(space.getId(), space);
        }
    }

    public HashMap<Integer, Space> getSpaces() throws SQLException {
        String query = "SELECT * FROM spaces";
        HashMap<Integer, Space> spaces = new HashMap<>();

        try (PreparedStatement ps = connection.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String location = rs.getString("location");
                String description = rs.getString("description");
                TypeSpace type = TypeSpace.valueOf(rs.getString("type"));
                int size = rs.getInt("size");
                boolean availability = rs.getBoolean("availability");
                String equipment = rs.getString("equipment");
                String policy = rs.getString("policy");
                int userId = rs.getInt("userId");

                Space space = new Space(id, name, location, description, type, size, availability, equipment, policy, userId);
                spaces.put(id, space);
            }
        }
        return spaces;
    }



}
