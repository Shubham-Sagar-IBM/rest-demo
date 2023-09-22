package com.example.restdemo.validation;

import com.example.restdemo.model.UserRegistration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class UserRegistrationValidator {
    public static ResponseEntity<String> validateUserRegistration(UserRegistration userRegistration) {
        if (userRegistration.getUsername() == null || userRegistration.getUsername().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: Username cannot be empty.");
        }

        if (userRegistration.getPassword() == null || userRegistration.getPassword().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: Password cannot be empty.");
        }

        if (userRegistration.getIpAddress() == null || userRegistration.getIpAddress().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: IP address cannot be empty.");
        }

        if (!isValidPassword(userRegistration.getPassword())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: Password must be at least 8 characters long and meet all criteria.");
        }

        return null; // Indicates successful validation
    }

    private static boolean isValidPassword(String password) {
        // Password must be at least 8 characters long and meet all criteria
        String regex = "^(?=.*[A-Z])(?=.*\\d)(?=.*[_#@$%\\.]).{8,}$";
        return password.matches(regex);
    }
}
