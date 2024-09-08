package services.interfaces;

import java.sql.SQLException;

public interface MigrationServiceInterface {

    void migrateRolesTable() throws SQLException;
    void migrateUsersTable() throws SQLException;
}
