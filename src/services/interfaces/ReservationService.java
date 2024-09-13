package services.interfaces;

import java.sql.SQLException;
import java.time.LocalDate;

public interface  ReservationService {

    public void createReservation(LocalDate startDate, LocalDate endDate, boolean status, int userId, int spaceId, Integer additionalServiceId) throws SQLException;

    }
