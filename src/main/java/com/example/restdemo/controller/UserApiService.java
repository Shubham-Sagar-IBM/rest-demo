package com.example.restdemo.controller;

import com.example.restdemo.model.UserRegistration;
import com.example.restdemo.service.UserLocationService;
import com.example.restdemo.util.UserWelcomeMessageGenerator;
import com.example.restdemo.validation.UserRegistrationValidator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/user")
public class UserApiService {
    @PostMapping
    public ResponseEntity<String> checkUserCredential(@RequestBody UserRegistration userRegistration) {
        ResponseEntity<String> validationResponse = UserRegistrationValidator.validateUserRegistration(userRegistration);

        if (validationResponse != null) {
            return validationResponse;
        }

        String ipAddress = userRegistration.getIpAddress();
        ResponseEntity<String> locationResponse = UserLocationService.getUserLocation(ipAddress);

        if (locationResponse.getStatusCode() != HttpStatus.OK) {
            return locationResponse;
        }

        String city = locationResponse.getBody();
        String uniqueId = UserWelcomeMessageGenerator.generateUniqueId();
        String message = UserWelcomeMessageGenerator.generateWelcomeMessage(uniqueId, userRegistration.getUsername(), city);

        return ResponseEntity.ok(message);
    }
}
