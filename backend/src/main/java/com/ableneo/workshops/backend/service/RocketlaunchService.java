package com.ableneo.workshops.backend.service;

import co.elastic.apm.api.CaptureSpan;
import com.ableneo.workshops.backend.model.Agency;
import com.ableneo.workshops.backend.model.Launch;
import com.ableneo.workshops.backend.model.LaunchSite;
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
import java.nio.charset.StandardCharsets;
import java.util.Random;

@Service
@Slf4j
@RequiredArgsConstructor
public class RocketlaunchService {

    final RestTemplate restTemplate;
    final ObjectMapper objectMapper;


    @Value("${rocketlaunch.next.url}")
    private String rocketLaunchNextUrl;

    @Value("${rocketlaunch.id.url}")
    private String rocketLaunchIdUrl;


    public Launch getLaunch(Long id) throws IOException, InterruptedException {
        ResponseEntity<String> responseEntity = restTemplate.exchange(rocketLaunchIdUrl+id, HttpMethod.GET, null, String.class);
        JsonNode node = objectMapper.readTree(responseEntity.getBody().getBytes(StandardCharsets.UTF_8));

        return transformLaunch(node.get("launches").get(0));
    }

    @CaptureSpan
    public Launch getUpcomingLaunch() throws IOException, InterruptedException {
        ResponseEntity<String> responseEntity = restTemplate.exchange(rocketLaunchNextUrl+"20", HttpMethod.GET, null, String.class);
        JsonNode node = objectMapper.readTree(responseEntity.getBody().getBytes(StandardCharsets.UTF_8));
        Random rand = new Random();

        return transformLaunch(node.get("launches").get(rand.nextInt(10)));
    }

    @CaptureSpan
    private Launch transformLaunch(JsonNode launchNode) throws InterruptedException {
        Thread.sleep(2000);
        Launch launch = new Launch();
        launch.setId(launchNode.get("id").asLong());
        launch.setDate(launchNode.get("windowstart").asText());
        launch.setName(launchNode.get("name").asText());

        log.info("Retrieving id "+launch.getId());

        Agency agency = new Agency();
        if (launchNode.get("rocket").has("agencies") && !launchNode.get("rocket").get("agencies").isNull()) {
            agency.setName(launchNode.get("rocket").get("agencies").get(0).get("name").asText());
        } else {
            agency.setName("Unknown");
        }
        launch.setAgency(agency);

        LaunchSite launchSite = new LaunchSite();
        launchSite.setName(launchNode.get("location").get("name").asText());
        launchSite.setCountry(launchNode.get("location").get("countryCode").asText());
        launch.setLaunchSite(launchSite);

        return launch;
    }

}
