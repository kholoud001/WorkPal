package utils;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordUtils {

    /**
     * Hashes the given password using SHA-256 algorithm.
     *
     * @param password the password to be hashed
     * @return the hashed password in hexadecimal format
     * @throws NoSuchAlgorithmException if the hashing algorithm is not available
     */
    public static String hashPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hashedBytes = digest.digest(password.getBytes());
        //System.out.println("hashed bytes " +hashedBytes);
        StringBuilder hexString = new StringBuilder();
        for (byte b : hashedBytes) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }
}
