package GUI;

import entities.User;
import services.interfaces.UserService;
import utils.ValidateUtils;

import java.security.NoSuchAlgorithmException;
import java.util.Optional;
import java.util.Scanner;



public class AdminGUI {

    private final UserService userService;
    private final User currentUser;
    private final Scanner scanner;



    public AdminGUI(UserService userService, User currentUser, Scanner scanner) {
        this.userService = userService;
        this.currentUser = currentUser;
        this.scanner = scanner;
    }

    public void displayMenuAdmin() {

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
                    updateExistingUser();
                    break;
                case 3:
                    deleteUser();
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

        // Gathering input from the admin
        System.out.println("*********** Add New User ***********");
        System.out.println("Enter the name:");
        String name = scanner.nextLine();

        System.out.println("Enter the password:");
        String password = scanner.nextLine();

        // Validate Email
        String email;
        while (true) {
            System.out.println("Enter Email:");
            email = scanner.nextLine();
            if (ValidateUtils.isValidEmail(email)) {
                break;
            } else {
                System.out.println("Invalid email format. Please try again.");
            }
        }

        // Validate Phone Number
        String phone;
        while (true) {
            System.out.println("Enter Phone (9 digits):");
            phone = scanner.nextLine();
            if (ValidateUtils.isValidPhone(phone)) {
                break;
            } else {
                System.out.println("Invalid phone number format. It should be exactly 9 digits.");
            }
        }

        System.out.println("Enter the address:");
        String address = scanner.nextLine();

        System.out.println("Enter the profile picture (URL or Path):");
        String profilePicture = scanner.nextLine();

        System.out.println("Enter the role ID (1 for Admin, 2 for Member, 3 for Manager):");
        Integer roleId = Integer.parseInt(scanner.nextLine());

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

    public void updateExistingUser() {

        System.out.println("*********** Update User ***********");

        // Get user ID to update
        System.out.println("Enter the User ID to update:");
        Integer userId = Integer.parseInt(scanner.nextLine());

        // Prompt for new values (can be left empty to keep the existing value)
        System.out.println("Enter the new name (leave blank to keep the current name):");
        String name = scanner.nextLine();

        System.out.println("Enter the new password (leave blank to keep the current password):");
        String password = scanner.nextLine();

        // Validate Email
        String email;
        while (true) {
            System.out.println("Enter new Email (leave blank to keep the current email):");
            email = scanner.nextLine();
            if (email.isEmpty() || ValidateUtils.isValidEmail(email)) {
                break;
            } else {
                System.out.println("Invalid email format. Please try again.");
            }
        }

        // Validate Phone Number
        String phone;
        while (true) {
            System.out.println("Enter new Phone (leave blank to keep the current phone, 9 digits):");
            phone = scanner.nextLine();
            if (phone.isEmpty() || ValidateUtils.isValidPhone(phone)) {
                break;
            } else {
                System.out.println("Invalid phone number format. It should be exactly 9 digits.");
            }
        }

        System.out.println("Enter new address (leave blank to keep the current address):");
        String address = scanner.nextLine();

        System.out.println("Enter new profile picture (leave blank to keep the current profile picture):");
        String profilePicture = scanner.nextLine();

        // Role input
        System.out.println("Enter the new role ID (leave blank to keep the current role):");
        String roleIdInput = scanner.nextLine();
        Integer roleId = roleIdInput.isEmpty() ? null : Integer.parseInt(roleIdInput);

        try {
            Optional<User> updatedUserOptional = userService.updateUser(
                    currentUser, userId, name, password, email, phone, address, profilePicture, roleId
            );

            if (updatedUserOptional.isPresent()) {
                System.out.println("User updated successfully! Name: " + updatedUserOptional.get().getName());
            } else {
                System.out.println("Failed to update user.");
            }
        } catch (NoSuchAlgorithmException e) {
            System.out.println("Error: Could not update user due to password hashing issues.");
        }
    }

    public void deleteUser() {

        // Prompt admin to enter the user ID to delete
        System.out.println("*********** Delete User ***********");
        System.out.println("Enter the User ID to delete:");
        int userId = Integer.parseInt(scanner.nextLine());

        Optional<User> deletedUserOptional = userService.deleteUser(currentUser, userId);

        if (deletedUserOptional.isPresent()) {
            User deletedUser = deletedUserOptional.get();
            System.out.println("User deleted successfully: " + deletedUser.getName());
        } else {
            System.out.println("Failed to delete the user or user not found.");
        }
    }





}
