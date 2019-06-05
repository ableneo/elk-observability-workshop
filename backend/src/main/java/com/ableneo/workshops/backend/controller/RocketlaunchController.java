package com.ableneo.workshops.backend.controller;

import com.ableneo.workshops.backend.model.Launch;
import com.ableneo.workshops.backend.service.RocketlaunchService;
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
        } catch (IOException | InterruptedException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override public ResponseEntity<Launch> getLaunch() {
        try {
            return new ResponseEntity<>(rocketlaunchService.getUpcomingLaunch(), HttpStatus.OK);
        } catch (IOException | InterruptedException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
