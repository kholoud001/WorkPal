package repositories.implementations;

import config.DatabaseConfig;
import entities.AdditionalService;
import entities.Space;
import repositories.interfaces.AdditionalServiceRepository;
import repositories.interfaces.UserRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;

public class AdditionalServiceRepositoryImp implements AdditionalServiceRepository {

    Connection connection;
    UserRepository userRepository;
    HashMap<Integer, AdditionalService> additionalServices ;


    public AdditionalServiceRepositoryImp(Connection connection, UserRepository userRepository) {
        this.connection = connection;
        this.userRepository = userRepository;
        this.additionalServices = new HashMap<>();
    }
    public void addAdditionalService(AdditionalService service) throws SQLException {
        String query = "INSERT INTO additional_services (name, quantity, price, userId) VALUES (?, ?, ?, ?)";
        try (Connection connection = DatabaseConfig.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, service.getName());
            preparedStatement.setInt(2, service.getQuantity());
            preparedStatement.setDouble(3, service.getPrice());
            preparedStatement.setInt(4, service.getUserId());
            preparedStatement.executeUpdate();
        }
    }
}
