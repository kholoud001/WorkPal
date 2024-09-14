package services.implementations;

import entities.Reservation;
import repositories.interfaces.ReservationRepository;
import repositories.interfaces.UserRepository;
import services.interfaces.ReservationService;

import services.interfaces.UserService;
import utils.GMailer;

import java.sql.SQLException;
import java.time.LocalDate;

public class ReservationServiceimp implements ReservationService {
    private ReservationRepository reservationRepository;
    private UserService userService;

    public ReservationServiceimp(ReservationRepository reservationRepository, UserService userService) {
        this.reservationRepository = reservationRepository;
        this.userService = userService;
    }

    public void createReservation(LocalDate startDate, LocalDate endDate, boolean status, int userId, int spaceId, Integer additionalServiceId) throws  SQLException {
        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("End date must be after start date.");
        }

        Reservation reservation = new Reservation(0, startDate, endDate, status, userId, spaceId, additionalServiceId);
        reservationRepository.add(reservation);

        //Send email
        String userEmail = userService.getUserEmailById(userId);
        System.out.println(userEmail);

        String subject = "Reservation Confirmation";
        String body = String.format("Dear User,\n\nYour reservation for space ID %d from %s to %s has been successfully made.\n\nThank you!",
                spaceId, startDate, endDate);

        try {
            GMailer.sendEmail(userEmail, subject, body);
            System.out.println("Confirmation email sent! Please check your inbox");
        } catch (Exception e) {
            System.err.println("Error sending confirmation email: " + e.getMessage());
        }

    }

    }



