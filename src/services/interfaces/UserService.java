package services.interfaces;

import entities.User;

import java.util.Optional;

public interface UserService {
    public boolean register(String name, String password, String email, String phone, String address, String profilePicture, Integer roleId) ;
    public Optional<User> login(String email, String password);
    }
