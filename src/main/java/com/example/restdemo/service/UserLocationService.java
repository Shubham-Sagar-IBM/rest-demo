package com.example.restdemo.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class UserLocationService {
    public static ResponseEntity<String> getUserLocation(String ipAddress) {
        String city = "";

        try {
            HttpClient httpClient = HttpClients.createDefault();
            String apiUrl = "http://ip-api.com/json/" + ipAddress;
            HttpGet httpGet = new HttpGet(apiUrl);
            HttpResponse response = httpClient.execute(httpGet);
            HttpEntity entity = response.getEntity();

            if (entity != null) {
                String responseBody = EntityUtils.toString(entity);
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonNode = objectMapper.readTree(responseBody);

                // Check if "country" field exists in the JSON response
                if (jsonNode.has("country")) {
                    String country = jsonNode.get("country").asText();
                    if (!"Canada".equalsIgnoreCase(country)) {
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: User is not in Canada.");
                    }
                } else {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: Country information not found.");
                }

                // Check if "city" field exists in the JSON response
                if (jsonNode.has("city")) {
                    city = jsonNode.get("city").asText();
                } else {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: City information not found.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: An error occurred.");
        }
        return ResponseEntity.ok(city);
    }
}
