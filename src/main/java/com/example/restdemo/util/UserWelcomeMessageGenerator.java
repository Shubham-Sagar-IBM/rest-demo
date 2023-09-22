package com.example.restdemo.util;

import java.util.UUID;

public class UserWelcomeMessageGenerator {
    public static String generateWelcomeMessage(String uniqueId, String username, String city) {
        return uniqueId + " Welcome " + username + " from " + city;
    }

    public static String generateUniqueId() {
        // Generate a unique ID using UUID
        return UUID.randomUUID().toString();
    }

}
