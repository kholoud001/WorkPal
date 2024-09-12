package GUI;

import Enums.TypeSpace;
import entities.Space;
import entities.User;
import services.interfaces.SpaceService;
import services.interfaces.UserService;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Optional;
import java.util.Scanner;

public class ManagerGUI {
    private UserService userService;
    private SpaceService spaceService;
    private User currentUser;
    private Scanner scanner;

    public ManagerGUI(UserService userService,User currentUser, SpaceService spaceService, Scanner scanner) {
        this.userService = userService;
        this.currentUser = currentUser;
        this.spaceService = spaceService;
        this.scanner = scanner;
    }

    public void displayMenuManager() {
        boolean exit = false;
        while (!exit) {
            System.out.println("\n*** Manager Menu ***");
            System.out.println("1. Add New Space");
            System.out.println("2. View All Spaces");
            System.out.println("3. Delete Space");
            System.out.println("0. Logout");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    addNewSpace();
                    break;
                case "2":
                    displayAllSpaces();
                    break;
                case "3":
                    deleteSpace();
                    break;
                case "0":
                    System.out.println("Logging out...");
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    public void addNewSpace() {
        System.out.println("*********** Add New Space ***********");

        System.out.print("Enter the space name: ");
        String name = scanner.nextLine();

        System.out.print("Enter the location: ");
        String location = scanner.nextLine();

        System.out.print("Enter the description: ");
        String description = scanner.nextLine();

        System.out.print("Enter the size (seats): ");
        int size = Integer.parseInt(scanner.nextLine());

        System.out.print("Is the space available (true/false): ");
        boolean availability = Boolean.parseBoolean(scanner.nextLine());

        System.out.print("Enter the equipment (comma-separated): ");
        String equipment = scanner.nextLine();

        System.out.print("Enter the policy: ");
        String policy = scanner.nextLine();

        // Assuming Type is an enum, select type
        System.out.print("Enter the type (MEETING_ROOM, WORKSPACE): ");
        String typeInput = scanner.nextLine();
        TypeSpace type = TypeSpace.valueOf(typeInput.toUpperCase());

        // Call the service to add the new space
        spaceService.addSpace(currentUser, name, location, description, type, size, availability, equipment, policy);
    }

    public void displayAllSpaces() {
        System.out.println("*********** All Spaces ***********");
        try {
            HashMap<Integer, Space> spaces = spaceService.getAllSpaces();

            if (spaces.isEmpty()) {
                System.out.println("No spaces found.");
            } else {
                for (Space space : spaces.values()) {
                    System.out.println("ID: " + space.getId());
                    System.out.println("Name: " + space.getName());
                    System.out.println("Location: " + space.getLocation());
                    System.out.println("Description: " + space.getDescription());
                    System.out.println("Type: " + space.getType());
                    System.out.println("Size: " + space.getSize());
                    System.out.println("Availability: " + (space.isAvailability() ? "Available" : "Not Available"));
                    System.out.println("Equipment: " + space.getEquipment());
                    System.out.println("Policy: " + space.getPolicy());
                    System.out.println("------------------------------------");
                }
            }
        } catch (SQLException e) {
            System.out.println("Error fetching spaces: " + e.getMessage());
        }
    }

    public void deleteSpace() {

        displayAllSpaces();

        System.out.println("\n*********** Delete Space ***********");

        // Ask the user for the space ID to delete
        System.out.print("Enter the ID of the space to delete: ");
        int spaceId = Integer.parseInt(scanner.nextLine());

        try {
            // Call the service method to delete the space
            Optional<Space> deletedSpace = spaceService.deleteSpace(currentUser, spaceId);

            // Check the result
            if (deletedSpace.isPresent()) {
                System.out.println("Space deleted successfully.");
            } else {
                System.out.println("Failed to delete the space. Either you don't have permission or the space does not exist.");
            }

        } catch (Exception e) {
            System.out.println("Error deleting space: " + e.getMessage());
        }
    }






}
