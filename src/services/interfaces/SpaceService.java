package services.interfaces;

import Enums.TypeSpace;
import entities.Space;
import entities.User;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Optional;

public interface SpaceService {

    public void addSpace(User currentUser, String name, String location, String description, TypeSpace type, int size, boolean availability, String equipment, String policy) ;

    public HashMap<Integer, Space> getAllSpaces() throws SQLException;

    public Optional<Space> deleteSpace(User currentUser, Integer spaceId);
}
