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

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class RocketlaunchService {

    final RestTemplate restTemplate;
    final ObjectMapper objectMapper;


    @Value("${rocketlaunch.service.url.launches}")
    private String rocketLaunchServiceUrl;

    public Launch getLaunch(Long id) {
        ResponseEntity<Launch> responseEntity = restTemplate.exchange(rocketLaunchServiceUrl+id, HttpMethod.GET, null, Launch.class);

        return responseEntity.getBody();
    }

    @CaptureSpan
    public Launch[] getLaunches() {
        ResponseEntity<Launch[]> responseEntity = restTemplate.exchange(rocketLaunchServiceUrl, HttpMethod.GET, null, Launch[].class);
        try {
            // I think we should let our application rest for a while
            Thread.sleep(4300);
        } catch (InterruptedException e) {
            log.error("Some exception during very important sleep phase.");
        }
        return responseEntity.getBody();
    }

}
