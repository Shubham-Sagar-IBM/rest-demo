package com.example.restdemo.controller;

import com.example.restdemo.model.UserRegistration;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.*;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import java.util.UUID;

@RestController
@RequestMapping("/user")
public class UserApiService {
    UserRegistration userRegistration;
    @PostMapping
    public String checkUserCredential(@RequestBody UserRegistration userRegistration){
        this.userRegistration = userRegistration;
        String Username = userRegistration.getUsername();
        String Password = userRegistration.getPassword();
        String ipAddress = userRegistration.getIpAddress();
        System.out.println(" ipaddress ");
        System.out.println(ipAddress);
        String uniqueId = generateUniqueId();
        String city = "";

        if (Username == null || Username.isEmpty()) {
            return "Error: Username cannot be empty.";
        }

        if (Password == null || Password.isEmpty()) {
            return "Error: Password cannot be empty.";
        }

        if (ipAddress == null || ipAddress.isEmpty()) {
            return "Error: IP address cannot be empty.";
        }

        // Check if password meets the specified criteria
        if (!isValidPassword(Password)) {
            return "Error: Password must be at least 8 characters long and meet all criteria.";
        }

        try {
            HttpClient httpClient = HttpClients.createDefault();
            String apiUrl = "http://ip-api.com/json/" + ipAddress;
            HttpGet httpGet = new HttpGet(apiUrl);
            HttpResponse response = httpClient.execute(httpGet);
            HttpEntity entity = response.getEntity();

            if (entity != null) {
                String responseBody = EntityUtils.toString(entity);
                System.out.println(" responseBody ");
                System.out.println(responseBody);
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonNode = objectMapper.readTree(responseBody);
                String country = jsonNode.get("country").asText();
                if (!"Canada".equalsIgnoreCase(country)) {
                    return "Error: User is not in Canada.";
                }
                city = jsonNode.get("city").asText();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return uniqueId+" Welcome "+Username +" from "+city;
    }
    private boolean isValidPassword(String password) {
        // Password must be at least 8 characters long and meet all criteria
        String regex = "^(?=.*[A-Z])(?=.*\\d)(?=.*[_#@$%\\.]).{8,}$";
        return password.matches(regex);
    }

    private String generateUniqueId() {
        // Generate a unique ID using UUID
        return UUID.randomUUID().toString();
    }
}

