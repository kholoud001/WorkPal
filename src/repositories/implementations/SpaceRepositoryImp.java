package repositories.implementations;

import entities.Space;
import repositories.interfaces.SpaceRepository;

import java.sql.Connection;
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
}
