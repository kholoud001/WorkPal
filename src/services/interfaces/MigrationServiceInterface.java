package services.interfaces;

import java.sql.SQLException;

public interface MigrationServiceInterface {

    void migrateUsersTable() throws SQLException;
}
