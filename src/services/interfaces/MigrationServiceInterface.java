package services.interfaces;

import config.DatabaseConfig;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public interface MigrationServiceInterface {

    void migrateRolesTable() throws SQLException;
    void migrateUsersTable() throws SQLException;
    public void migrateSpacesTable() throws SQLException ;
    public void migrateEventsTable()  throws SQLException;
    public void migrateFeedbackTable() throws SQLException;
    public void migrateAdditionalServiceable() throws SQLException;
    public void migrateReservationsTable() throws SQLException;
    public void migrateFavoritesTable() throws SQLException;
    void migrateSubsTable() throws SQLException;




}
