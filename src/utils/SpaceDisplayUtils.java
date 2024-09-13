package utils;

import entities.Space;

import java.util.HashMap;

public class SpaceDisplayUtils {

    public static void displayAllSpaces(HashMap<Integer, Space> spaces) {
        System.out.println("*********** All Spaces ***********");

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
    }
}
