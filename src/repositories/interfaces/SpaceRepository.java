package repositories.interfaces;

import entities.Space;

import java.sql.SQLException;

public interface SpaceRepository {

    public void addSpace(Space space) throws SQLException;

}
