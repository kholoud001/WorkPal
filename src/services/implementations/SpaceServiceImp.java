package services.implementations;

import Enums.TypeSpace;
import entities.Space;
import entities.User;
import repositories.implementations.SpaceRepositoryImp;
import repositories.interfaces.SpaceRepository;
import repositories.interfaces.UserRepository;
import services.interfaces.SpaceService;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Optional;

public class SpaceServiceImp implements SpaceService {

    private UserRepository userRepository;
    private SpaceRepository spaceRepository;
    private Connection connection;

    public SpaceServiceImp(UserRepository userRepository, SpaceRepository spaceRepository, Connection connection) {
        this.userRepository = userRepository;
        this.spaceRepository = spaceRepository;
        this.connection = connection;
    }

    public void addSpace(User currentUser, String name, String location, String description, TypeSpace type, int size, boolean availability, String equipment, String policy) {
        if (currentUser.getRole().getId() != 3) {
            System.out.println("You are not allowed to add a space! You're not a Manager.");
            return;
        }

        try {
            Space space = new Space(0, name, location, description, type, size, availability, equipment, policy, currentUser.getId());
            spaceRepository.addSpace(space);

            System.out.println("Space added successfully.");
        } catch (SQLException e) {
            System.out.println("Error adding space: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("An unexpected error occurred while adding the space: " + e.getMessage());
        }
    }

    public HashMap<Integer, Space> getAllSpaces() throws SQLException {
        return spaceRepository.getSpaces();
    }

}
