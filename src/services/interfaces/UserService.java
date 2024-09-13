package services.interfaces;

import entities.Role;
import entities.User;
import utils.PasswordUtils;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.Optional;

public interface UserService {
    public boolean register(String name, String password, String email, String phone, String address, String profilePicture, Integer roleId) throws NoSuchAlgorithmException;
    public Optional<User> login(String email, String password);
    public Optional<User> addMemberOrManager(User currentUser, String name, String password, String email, String phone, String address, String profilePicture, Integer roleId) throws NoSuchAlgorithmException ;
    public Optional<User> updateUser(User currentUser, Integer userId, String name, String password, String email, String phone, String address, String profilePicture, Integer roleId) throws NoSuchAlgorithmException;
    public Optional<User> deleteUser(User currentUser, Integer userId);
    public String getUserEmailById(int userId) throws SQLException;

}
