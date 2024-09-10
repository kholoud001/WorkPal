package GUI;

import entities.User;
import services.interfaces.UserService;

import java.security.NoSuchAlgorithmException;
import java.util.Optional;
import java.util.Scanner;



public class AdminGUI {

    private final UserService userService;
    private final User currentUser;


    public AdminGUI(UserService userService, User currentUser) {
        this.userService = userService;
        this.currentUser = currentUser;
    }

    public void displayMenuAdmin() {
        final Scanner scanner = null;

        int choix;
        do {
            // Display the menu
            System.out.println("\n*** Admin Management Panel: ***");
            System.out.println("1. Add User");
            System.out.println("2. Modify User");
            System.out.println("3. Delete User");

            System.out.println("0. Exit");
            System.out.print("Choose an option: ");

            choix = Integer.parseInt(scanner.nextLine());

            switch (choix) {
                case 1:
                    addNewUser();
                    break;
                case 2:

                    break;
                case 3:

                    break;
                case 0:
                    System.out.println("Exiting Admin Menu...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }

        } while (choix != 0);
    }




    public void addNewUser() {
        Scanner scanner = new Scanner(System.in);

        // Gathering input from the admin
        System.out.println("*********** Add New User ***********");
        System.out.println("Enter the name:");
        String name = scanner.nextLine();

        System.out.println("Enter the password:");
        String password = scanner.nextLine();

        System.out.println("Enter the email:");
        String email = scanner.nextLine();

        System.out.println("Enter the phone:");
        String phone = scanner.nextLine();

        System.out.println("Enter the address:");
        String address = scanner.nextLine();

        System.out.println("Enter the profile picture (URL or Path):");
        String profilePicture = scanner.nextLine();

        System.out.println("Enter the role ID (1 for Admin, 2 for Member, 3 for Manager):");
        Integer roleId = Integer.parseInt(scanner.nextLine());

        // Call the service method to add the user
        try {
            Optional<User> newUserOptional = userService.addMemberOrManager(
                    currentUser, name, password, email, phone, address, profilePicture, roleId
            );

            if (newUserOptional.isPresent()) {
                System.out.println("New user added successfully! Name: " + newUserOptional.get().getName());
            } else {
                System.out.println("Failed to add new user.");
            }
        } catch (NoSuchAlgorithmException e) {
            System.out.println("Error: Could not add user due to password hashing issues.");
        }
    }

}
