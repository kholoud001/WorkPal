package repositories.interfaces;

import entities.AdditionalService;

import java.sql.SQLException;

public interface AdditionalServiceRepository {

    public void addAdditionalService(AdditionalService service) throws SQLException;
}
