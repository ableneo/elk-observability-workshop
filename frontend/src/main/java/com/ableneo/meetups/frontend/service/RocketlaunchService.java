package com.ableneo.meetups.frontend.service;

import co.elastic.apm.api.CaptureSpan;
import co.elastic.apm.api.Span;
import com.ableneo.meetups.frontend.model.Launch;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
@RequiredArgsConstructor
public class RocketlaunchService {

    final RestTemplate restTemplate;
    final ObjectMapper objectMapper;


    @Value("${rocketlaunch.service.url}")
    private String rocketLaunchServiceUrl;

    public Launch getLaunch(Long id) {
        ResponseEntity<Launch> responseEntity = restTemplate.exchange(rocketLaunchServiceUrl+id, HttpMethod.GET, null, Launch.class);

        return responseEntity.getBody();
    }

    @CaptureSpan
    public Launch getUpcomingLaunch() {
        ResponseEntity<Launch> responseEntity = restTemplate.exchange(rocketLaunchServiceUrl, HttpMethod.GET, null, Launch.class);

        return responseEntity.getBody();
    }

}
