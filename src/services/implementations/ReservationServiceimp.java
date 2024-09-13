package services.implementations;

import entities.Reservation;
import repositories.interfaces.ReservationRepository;
import services.interfaces.ReservationService;

import java.sql.SQLException;
import java.time.LocalDate;

public class ReservationServiceimp implements ReservationService {
    private ReservationRepository reservationRepository;

    public ReservationServiceimp(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public void createReservation(LocalDate startDate, LocalDate endDate, boolean status, int userId, int spaceId, Integer additionalServiceId) throws  SQLException {
        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("End date must be after start date.");
        }

        Reservation reservation = new Reservation(0, startDate, endDate, status, userId, spaceId, additionalServiceId);
        reservationRepository.add(reservation);
    }


}
