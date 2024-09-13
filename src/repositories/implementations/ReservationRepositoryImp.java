package repositories.implementations;

import entities.Reservation;
import repositories.interfaces.ReservationRepository;

import java.sql.*;
import java.time.LocalDate;

public class ReservationRepositoryImp implements ReservationRepository {

    private Connection connection;

    public ReservationRepositoryImp(Connection connection) {
        this.connection = connection;
    }

    public void add(Reservation reservation) throws SQLException {
        String query = "INSERT INTO reservations (start_date, end_date, status, userId, spaceId, additional_serviceId) " +
                "VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setDate(1, Date.valueOf(reservation.getStartDate()));
            stmt.setDate(2, Date.valueOf(reservation.getEndDate()));
            stmt.setBoolean(3, reservation.isStatus());
            stmt.setInt(4, reservation.getUserId());
            if (reservation.getSpaceId() != null) {
                stmt.setInt(5, reservation.getSpaceId());
            } else {
                stmt.setNull(5, Types.INTEGER);
            }
            if (reservation.getAdditionalServiceId() != null) {
                stmt.setInt(6, reservation.getAdditionalServiceId());
            } else {
                stmt.setNull(6, Types.INTEGER);
            }
            stmt.executeUpdate();
        }
    }
}
