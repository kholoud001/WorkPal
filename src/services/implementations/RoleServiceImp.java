package services.implementations;

import entities.Role;
import repositories.interfaces.RoleRepository;
import services.interfaces.RoleService;

import java.sql.Connection;
import java.util.List;

public class RoleServiceImp implements RoleService {
    RoleRepository roleRepository;
    Connection connection;

    public RoleServiceImp(RoleRepository roleRepository, Connection connection) {
        this.roleRepository = roleRepository;
        this.connection = connection;
    }

    public List<Role> getAllRoles() {
        return roleRepository.findRoleApartAdmin();
    }
}
