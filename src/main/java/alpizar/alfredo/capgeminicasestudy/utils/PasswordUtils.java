package alpizar.alfredo.capgeminicasestudy.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordUtils {

    // Encryte Password with BCryptPasswordEncoder
    public static String encryptedPassword(String password) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.encode(password);
    }

    public static void main(String[] args) {
        String password = "123";
        String encrytedPassword = encryptedPassword(password);

        System.out.println("Encryted Password: " + encrytedPassword);
    }

}