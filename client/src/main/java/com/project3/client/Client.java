package com.project3.client;

import com.project3.client.dto.MeasurementDTO;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

public class Client {
    public static void main(String[] args) {
        String sensorName = "SuperSensor";
        addRandomValuesToMeasurements(1000, sensorName);
    }

    private static void addRandomValuesToMeasurements(int amount, String sensorName) {
        final double maxTemp = 45.;
        final double minTemp = 0.;

        double temp;
        boolean isRaining;
        Random random = new Random();
        for (int i = 0; i < amount; i++) {
            temp = random.nextDouble(minTemp, maxTemp);
            isRaining = random.nextBoolean();
            addMeasures(temp, isRaining, sensorName);
        }
    }

    /**
     * If Sensor does not exist
     * in database or add another one
     *
     * @param sensorName name of sensor
     */
    private static void registerSensor(String sensorName) {
        RestTemplate restTemplate = new RestTemplate();
        Map<String, String> jsonToSend = new HashMap<>();
        jsonToSend.put("name", sensorName);
        HttpEntity<Map<String, String>> req = new HttpEntity<>(jsonToSend);
        String URL = "http://localhost:8080/sensors/registration";
        String response = restTemplate.postForObject(URL, req, String.class);
        System.out.println(response);
    }

    /**
     * Adding measurement to DB
     *
     * @param temp      temperature
     * @param isRaining is raining?
     * @param sensor    name of sensor
     */
    private static void addMeasures(Double temp, Boolean isRaining, String sensor) {
        final String URL = "http://localhost:8080/measurement/add";
        Map<String, Object> jsonToSend = new HashMap<>();
        jsonToSend.put("value", temp);
        jsonToSend.put("raining", isRaining);
        jsonToSend.put("sensor", Map.of("name", sensor));
        makePostRequestWithJSONData(URL, jsonToSend);
    }

    /**
     * Making a request with parameters
     *
     * @param url      URL to server
     * @param jsonData data in json format
     */
    private static void makePostRequestWithJSONData(String url, Map<String, Object> jsonData) {
        final RestTemplate restTemplate = new RestTemplate();
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Object> request = new HttpEntity<>(jsonData, headers);

        try {
            restTemplate.postForObject(url, request, String.class);
            System.out.println("Measurement has been successfully added to server ");
        } catch (HttpClientErrorException e) {
            System.out.println("ERROR!");
            System.out.println(e.getMessage());
        }
    }

    /**
     * Get all measurements from DB
     * @return List of Double or empty List
     */
    public static List<Double> getAllMeasurements() {
        final RestTemplate restTemplate = new RestTemplate();
        final String url = "http://localhost:8080/measurement";

        ResponseEntity<MeasurementDTO[]> responseEntity = restTemplate.getForEntity(url, MeasurementDTO[].class);
        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            MeasurementDTO[] measurements = responseEntity.getBody();
            if (measurements != null) {
                return Arrays.stream(measurements)
                        .map(MeasurementDTO::getValue)
                        .collect(Collectors.toList());
            }
        }
        return Collections.emptyList();
    }
}
