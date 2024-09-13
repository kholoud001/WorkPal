package services.implementations;

import entities.AdditionalService;
import repositories.interfaces.AdditionalServiceRepository;
import repositories.interfaces.SpaceRepository;
import repositories.interfaces.UserRepository;
import services.interfaces.AdditionalSService;

import java.sql.Connection;
import java.sql.SQLException;

public class AdditionalSServiceImp implements AdditionalSService {

    private  AdditionalServiceRepository additionalServiceRepository;
    private UserRepository userRepository;
    private Connection connection;


    public AdditionalSServiceImp(AdditionalServiceRepository additionalServiceRepository) {
        this.additionalServiceRepository = additionalServiceRepository;
//        this.userRepository = userRepository;
//        this.connection = connection;
    }


    public void addService(String name, int quantity, double price, int userId) throws SQLException {
        AdditionalService service = new AdditionalService(0, name, quantity, price, userId);

        additionalServiceRepository.addAdditionalService(service);
        System.out.println("Service added successfully!");
    }


}
