package services.interfaces;

public interface UserService {
    public boolean register(String name, String password, String email, String phone, String address, String profilePicture, Integer roleId) ;

    }
