package repositories.interfaces;

import entities.Space;

import java.sql.SQLException;
import java.util.HashMap;

public interface SpaceRepository {

    public void addSpace(Space space) throws SQLException;
    public HashMap<Integer, Space> getSpaces() throws SQLException;

}
