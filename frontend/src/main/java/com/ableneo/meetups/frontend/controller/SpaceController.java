package com.ableneo.meetups.frontend.controller;

import co.elastic.apm.api.ElasticApm;
import co.elastic.apm.api.Transaction;
import com.ableneo.meetups.frontend.model.Launch;
import com.ableneo.meetups.frontend.service.RocketlaunchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequiredArgsConstructor
public class SpaceController implements SpaceApi {

    final RocketlaunchService rocketlaunchService;

    @Override public ResponseEntity<Launch> getLaunch(Long id, @RequestHeader HttpHeaders headers) {
        if (id < 0) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        addUserInfo(headers);
        return new ResponseEntity<>(rocketlaunchService.getLaunch(id), HttpStatus.OK);

    }

    @Override
    public ResponseEntity<Launch[]> getLaunches(@RequestHeader HttpHeaders headers) {
        addUserInfo(headers);
        return new ResponseEntity<>(rocketlaunchService.getLaunches(), HttpStatus.OK);
    }

    private void addUserInfo(HttpHeaders headers){

        Transaction t = ElasticApm.currentTransaction();
        if(headers.get("Authorization") != null && !headers.get("Authorization").isEmpty()) {
            String auth = headers.get("Authorization").get(0);
            t.setUser(auth.split(":")[0],auth+"@localhost", auth.split(":")[0]);
        }
        t.setUser("UNK","unknown@unknown.com", "unkwnown");
    }
}
