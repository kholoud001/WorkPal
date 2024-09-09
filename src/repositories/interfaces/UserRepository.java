package repositories.interfaces;

import entities.Role;
import entities.User;

import java.util.Collection;
import java.util.Optional;

public interface UserRepository {
    public User addUser(User user);
    public Optional<User> findByEmail(String email);
    public Collection<User> findAll();
    public Collection<User> findUserByRole(Role role);
