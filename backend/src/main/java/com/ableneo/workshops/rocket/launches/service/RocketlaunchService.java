package com.ableneo.workshops.rocket.launches.service;

import co.elastic.apm.opentracing.ElasticApmTracer;
import com.ableneo.workshops.rocket.launches.model.Agency;
import com.ableneo.workshops.rocket.launches.model.Launch;
import com.ableneo.workshops.rocket.launches.model.LaunchSite;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.opentracing.Tracer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

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


    public Launch getLaunch(Long id) throws IOException {
        Tracer tracer = new ElasticApmTracer();
        tracer.buildSpan("GetLaunch").start();

        ResponseEntity<String> responseEntity = restTemplate.exchange(rocketLaunchIdUrl+id, HttpMethod.GET, null, String.class);
        JsonNode node = objectMapper.readTree(responseEntity.getBody().getBytes(StandardCharsets.UTF_8));

        tracer.activeSpan().finish();
        return transformLaunch(node.get("launches").get(0));
    }

    public Launch[] getLaunches() throws IOException {

        Tracer tracer = new ElasticApmTracer();
        tracer.buildSpan("GetLaunches").start();

        ResponseEntity<String> responseEntity = restTemplate.exchange(rocketLaunchNextUrl+"20", HttpMethod.GET, null, String.class);
        JsonNode node = objectMapper.readTree(responseEntity.getBody().getBytes(StandardCharsets.UTF_8));

        List<Launch> launchList = new ArrayList<>();
        for (int i = 0; i < node.get("launches").size(); i++) {
                launchList.add(transformLaunch(node.get("launches").get(i)));
        }

        tracer.activeSpan().finish();
        return launchList.toArray(new Launch[]{});
    }

    private Launch transformLaunch(JsonNode launchNode) {

        // I believe we should wait for rest request to complete!
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            log.error("Error during sleep", e);
        }

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
