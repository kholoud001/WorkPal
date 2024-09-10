package repositories.interfaces;

import entities.Role;

import java.util.List;
import java.util.Optional;

public interface RoleRepository {
    public Optional<Role> findById(int roleId);
    public  Optional<Role> findByName(String name);
    public List<Role> findAll();
    public List<Role> findRoleApartAdmin();
}
