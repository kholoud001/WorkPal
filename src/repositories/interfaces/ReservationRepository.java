package repositories.interfaces;

import entities.Reservation;

import java.sql.SQLException;

public interface ReservationRepository {
    public void add(Reservation reservation) throws SQLException;
}
