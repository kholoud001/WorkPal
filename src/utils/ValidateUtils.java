package utils;

import java.util.regex.Pattern;

public class ValidateUtils {

    // Email validation method using regex
    public static boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        return Pattern.matches(emailRegex, email);
    }

    // Phone validation method for exactly 9 digits
    public static boolean isValidPhone(String phone) {
        String phoneRegex = "\\d{9}";  // Phone must be exactly 9 digits
        return Pattern.matches(phoneRegex, phone);
    }
}
