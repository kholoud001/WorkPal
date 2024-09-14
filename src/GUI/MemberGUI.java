package GUI;

import entities.Space;
import entities.User;
import services.interfaces.AdditionalSService;
import services.interfaces.ReservationService;
import services.interfaces.SpaceService;
import services.interfaces.UserService;
import utils.GMailer;
import utils.SpaceDisplayUtils;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Scanner;

public class MemberGUI {

    private UserService userService;
    private SpaceService spaceService;
    private AdditionalSService additionalServiceService;
    private ReservationService reservationService;
    private User currentUser;
    private Scanner scanner;


//    public MemberGUI(UserService userService, SpaceService spaceService, AdditionalSService additionalServiceService, ReservationService reservationService, Scanner scanner) {
//        this.userService = userService;
//        this.spaceService = spaceService;
//        this.additionalServiceService = additionalServiceService;
//        this.reservationService = reservationService;
//        this.scanner = scanner ;
//    }

    public MemberGUI(ReservationService reservationService,SpaceService spaceService, User currentUser, Scanner scanner) {
        this.reservationService = reservationService;
        this.spaceService = spaceService;
        this.currentUser = currentUser;
        this.scanner = scanner;
    }


    public void displayMemberMenu() throws SQLException {
        int choice = -1;
        while (choice != 0) {
            System.out.println("\n==== Member Menu ====");
            System.out.println("1. Reserve a space");
//            System.out.println("2. View your reservations");
            System.out.println("2. Cancel Reservation");
            System.out.println("0. Logout");

            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    reserveSpace();
                    break;
                case 2:
                    cancelReservation();
                    break;
                case 0:
                    System.out.println("Logging out...");
                    break;
                default:
                    System.out.println("Invalid choice, please try again.");
            }
        }
    }

    public void displayAllSpaces() {
        try {
            HashMap<Integer, Space> spaces = spaceService.getAllSpaces();
            SpaceDisplayUtils.displayAllSpaces(spaces);
        } catch (SQLException e) {
            System.out.println("Error fetching spaces: " + e.getMessage());
        }
    }

    public void reserveSpace() throws SQLException {
        displayAllSpaces();
        System.out.print("\n Enter start date (yyyy-mm-dd): ");
        String startDateStr = scanner.nextLine();
        LocalDate startDate = LocalDate.parse(startDateStr);

        System.out.print("Enter end date (yyyy-mm-dd): ");
        String endDateStr = scanner.nextLine();
        LocalDate endDate = LocalDate.parse(endDateStr);

        System.out.print("Enter space ID: ");
        int spaceId = scanner.nextInt();

        System.out.print("Do you want to reserve additional services (yes/no)? ");
        scanner.nextLine();
        String reserveService = scanner.nextLine();
        Integer additionalServiceId = null;

        if (reserveService.equalsIgnoreCase("yes")) {
            System.out.print("Enter additional service ID: ");
            additionalServiceId = scanner.nextInt();
        }
        reservationService.createReservation(startDate, endDate, true, currentUser.getId(), spaceId, additionalServiceId);
        System.out.println("Reservation made successfully!");



    }

    public void cancelReservation() throws SQLException {
        System.out.print("Enter the reservation ID to cancel: ");
        int reservationId = scanner.nextInt();

        reservationService.cancelReservation(reservationId,currentUser.getId());
        System.out.println("Cancellation process complete.");
    }

    }
