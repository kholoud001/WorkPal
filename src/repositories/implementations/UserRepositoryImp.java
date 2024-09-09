package repositories.implementations;

import repositories.interfaces.UserRepository;

import java.sql.Connection;

public class UserRepositoryImp implements UserRepository {
    private Connection connection;

    public UserRepositoryImp(Connection connection) {
        this.connection = connection;
    }

}
