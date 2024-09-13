package GUI;

import Enums.TypeSpace;
import entities.Space;
import entities.User;
import services.interfaces.AdditionalSService;
import services.interfaces.SpaceService;
import services.interfaces.UserService;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Optional;
import java.util.Scanner;

public class ManagerGUI {
    private UserService userService;
    private SpaceService spaceService;
    private AdditionalSService additionalServiceService;
    private User currentUser;
    private Scanner scanner;

    public ManagerGUI(UserService userService,User currentUser, SpaceService spaceService, AdditionalSService additionalServiceService, Scanner scanner) {
        this.userService = userService;
        this.currentUser = currentUser;
        this.spaceService = spaceService;
        this.additionalServiceService = additionalServiceService;
        this.scanner = scanner;
    }

    public void displayMenuManager() throws SQLException {
        boolean exit = false;
        while (!exit) {
            System.out.println("\n*** Manager Menu ***");
            System.out.println("1. Add New Space");
            System.out.println("2. View All Spaces");
            System.out.println("3. Delete Space");
            System.out.println("4. Update Space details");
            System.out.println("5. Add additional service");
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
                case "4":
                    updateSpaceDetails();
                    break;
                case "5":
                    addAdditionalService();
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
                System.out.println("Failed to delete the space. Either you don't have permission or the space is available.");
            }

        } catch (Exception e) {
            System.out.println("Error deleting space: " + e.getMessage());
        }
    }

    public void updateSpaceDetails() {

        displayAllSpaces();
        System.out.println("\n*********** Update Space Details ***********");

        System.out.print("Enter the ID of the space you want to update: ");
        int spaceId = Integer.parseInt(scanner.nextLine());

        try {
            Optional<Space> spaceOptional = spaceService.findSpaceById(spaceId);

            if (spaceOptional.isPresent()) {
                Space space = spaceOptional.get();

                System.out.print("Enter the new name (" + space.getName() + "): ");
                String name = scanner.nextLine();
                if (name.isEmpty()) {
                    name = space.getName();
                }

                System.out.print("Enter the new location (" + space.getLocation() + "): ");
                String location = scanner.nextLine();
                if(location.isEmpty()){
                    location=space.getLocation();
                }

                System.out.print("Enter the new description (" + space.getDescription() + "): ");
                String description = scanner.nextLine();
                if (description.isEmpty()) {
                    description = space.getDescription();
                }

                System.out.print("Enter the new size (" + space.getSize() + "): ");
                String sizeInput = scanner.nextLine();
                int size = sizeInput.isEmpty() ? space.getSize() : Integer.parseInt(sizeInput);

                System.out.print("Is the space available (true/false): ");
                String availabilityInput = scanner.nextLine();
                boolean availability = availabilityInput.isEmpty() ? space.isAvailability() : Boolean.parseBoolean(availabilityInput);

                System.out.print("Enter the new equipment (" + space.getEquipment() + "): ");
                String equipment = scanner.nextLine();
                if(equipment.isEmpty()){
                    equipment = space.getEquipment();
                }

                System.out.print("Enter the new policy (" + space.getPolicy() + "): ");
                String policy = scanner.nextLine();
                if(policy.isEmpty()){
                    policy = space.getPolicy();
                }

                System.out.print("Enter the new type (MEETING_ROOM, WORKSPACE) [" + space.getType() + "]: ");
                String typeInput = scanner.nextLine();
                TypeSpace type = typeInput.isEmpty() ? space.getType() : TypeSpace.valueOf(typeInput.toUpperCase());

                Space updatedSpace = new Space(spaceId, name, location, description, type, size, availability, equipment, policy, currentUser.getId());

                boolean isUpdated = spaceService.updateSpace(updatedSpace);

                if (isUpdated) {
                    System.out.println("Space updated successfully.");
                } else {
                    System.out.println("Failed to update space.");
                }
            } else {
                System.out.println("Space with the provided ID does not exist.");
            }
        } catch (Exception e) {
            System.out.println("Error updating space: " + e.getMessage());
        }
    }


    ///////////////////////// Additional services Management //////////////////////////////////////

    private void addAdditionalService() throws SQLException {
        System.out.print("Enter service name: ");
        String name = scanner.nextLine();

        System.out.print("Enter quantity: ");
        int quantity = scanner.nextInt();

        System.out.print("Enter price: ");
        double price = scanner.nextDouble();

        int userId = currentUser.getId();

        additionalServiceService.addService(name, quantity, price, userId);
    }








}
