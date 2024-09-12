package repositories.interfaces;

import entities.Space;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Optional;

public interface SpaceRepository {

    public void addSpace(Space space) throws SQLException;
    public HashMap<Integer, Space> getSpaces() throws SQLException;
    public Space deleteSpace(Space space);
    public Optional<Space> findById(int spaceId);

}
