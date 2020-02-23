package com.ableneo.workshops.rocket.launches.controller;

import com.ableneo.workshops.rocket.launches.model.Launch;
import com.ableneo.workshops.rocket.launches.service.RocketlaunchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class RocketlaunchController implements RocketlaunchAPI {

    final RocketlaunchService rocketlaunchService;

    @Override public ResponseEntity<Launch> getLaunch(Long id) {
        try {
            if (id < 0) {
                throw new IOException("Bad args!");
            }
            return new ResponseEntity<>(rocketlaunchService.getLaunch(id), HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override public ResponseEntity<Launch[]> getLaunches() {
        try {
            return new ResponseEntity<>(rocketlaunchService.getLaunches(), HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
