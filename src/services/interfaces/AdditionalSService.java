package services.interfaces;

import entities.AdditionalService;

import java.sql.SQLException;

public interface AdditionalSService {

    public void addService(String name, int quantity, double price, int userId) throws SQLException;
}
