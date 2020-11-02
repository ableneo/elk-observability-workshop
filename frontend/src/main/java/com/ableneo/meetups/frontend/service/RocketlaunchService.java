package com.ableneo.meetups.frontend.service;

import co.elastic.apm.api.CaptureSpan;
import com.ableneo.meetups.frontend.model.Launch;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Arrays;

@Service
@Slf4j
@RequiredArgsConstructor
public class RocketlaunchService {

    final RestTemplate restTemplate;
    final ObjectMapper objectMapper;

    @Value("${nodejs.service.url.long}")
    private String nodejsBackendServiceUrlLongRunning;
    @Value("${nodejs.service.url.short}")
    private String nodejsBackendServiceUrlShortRunning;

    @Value("${rocketlaunch.service.url.launches}")
    private String rocketLaunchServiceUrl;


    public Launch getLaunch(Long id) {
        ResponseEntity<Launch> responseEntity = restTemplate.exchange(rocketLaunchServiceUrl+id, HttpMethod.GET, null, Launch.class);

        return responseEntity.getBody();
    }

    @CaptureSpan
    public Launch[] getLaunches() {
        ResponseEntity<Launch[]> responseEntity = restTemplate.exchange(rocketLaunchServiceUrl, HttpMethod.GET, null, Launch[].class);
        Launch[] launches = responseEntity.getBody();
        Arrays.stream(launches).forEach(this::extendWithLongCalculation);
        Arrays.stream(launches).forEach(this::extendWithShortCalculation);

        return launches;
    }

    @CaptureSpan("Extend with slow running calculation")
    private void extendWithLongCalculation(Launch launch) {
        ResponseEntity<String> response = restTemplate.exchange(nodejsBackendServiceUrlLongRunning, HttpMethod.GET, null, String.class);

        JsonNode jsonNode = null;
        try {
            jsonNode = objectMapper.readTree(response.getBody());
        } catch (IOException e) {
            e.printStackTrace();
        }

        launch.setLongCalculationResult(jsonNode.get("result").textValue());
    }

    @CaptureSpan("Extend with slow running calculation")
    private void extendWithShortCalculation(Launch launch) {
        ResponseEntity<String> response = restTemplate.exchange(nodejsBackendServiceUrlShortRunning, HttpMethod.GET, null, String.class);

        JsonNode jsonNode = null;
        try {
            jsonNode = objectMapper.readTree(response.getBody());
        } catch (IOException e) {
            e.printStackTrace();
        }

        launch.setShortCalculationResult(jsonNode.get("result").textValue());
    }
}
