package repositories.implementations;

import entities.Reservation;
import repositories.interfaces.ReservationRepository;

import java.sql.*;
import java.time.LocalDate;
import java.util.HashMap;

public class ReservationRepositoryImp implements ReservationRepository {

    private Connection connection;
    private HashMap<String, Reservation> reservations;

    public ReservationRepositoryImp(Connection connection) {

        this.connection = connection;
        this.reservations = new HashMap<>();
    }

//    public HashMap<String, Reservation> getReservations() throws SQLException {
//        String query= "SELECT * FROM reservations";
//        try(PreparedStatement stmt = connection.prepareStatement(query)){
//            ResultSet rs = stmt.executeQuery();
//
//
//        }
//    }

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

    public void cancelReservation(int reservationId) throws SQLException {
        String query = "UPDATE reservations SET status=? WHERE id=?";

        try(PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setBoolean(1, false);
            stmt.setInt(2, reservationId);
            int state= stmt.executeUpdate();
            if (state >0) {
                System.out.println("Reservation cancelled successfully.");
            } else {
                System.out.println("Reservation not found.");
            }
        }
    }
}
