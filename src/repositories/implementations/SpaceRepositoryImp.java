package repositories.implementations;

import Enums.TypeSpace;
import entities.Role;
import entities.Space;
import entities.User;
import repositories.interfaces.SpaceRepository;
import repositories.interfaces.UserRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Optional;

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

    public Space deleteSpace(Space space) {
        String query = "DELETE FROM spaces WHERE id = ? AND availability = false";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, space.getId());

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                //System.out.println("Space deleted successfully!");
                return space;
            } else {
                //System.out.println("Space deletion failed. No user found with the given ID.");
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error occurred while deleting space.");
            return null;
        }
    }

    public Optional<Space> findById(int spaceId) {
        String query = "SELECT * FROM spaces WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, spaceId);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Space space = new Space(
                            resultSet.getInt("id"),
                            resultSet.getString("name"),
                            resultSet.getString("location"),
                            resultSet.getString("description"),
                            TypeSpace.valueOf(resultSet.getString("type")),
                            resultSet.getInt("size"),
                            resultSet.getBoolean("availability"),
                            resultSet.getString("equipment"),
                            resultSet.getString("policy"),
                            resultSet.getInt("userId")
                    );
                    return Optional.of(space);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }







}
