package com.ableneo.meetups.frontend.controller;

import com.ableneo.meetups.frontend.model.Launch;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Api(tags = "Space info service")
@RequestMapping(value = "/space", produces = {"application/json"})
public interface SpaceApi {

    @ApiOperation(
        value = "Get launch",
        nickname = "getLaunch",
        response = Launch.class
    )
    @ApiResponses(
        value = {
            @ApiResponse(code = 200, message = "Success", response = Launch.class)
        }
    )
    @RequestMapping(
        value = "/launch/{id}",
        produces = {"application/json"},
        method = RequestMethod.GET
    ) ResponseEntity<Launch> getLaunch(@PathVariable(value = "id") Long id, @RequestHeader HttpHeaders headers);

    @ApiOperation(
        value = "Get launch",
        nickname = "getRandomImage",
        response = Launch.class
    )
    @ApiResponses(
        value = {
            @ApiResponse(code = 200, message = "Success", response = Launch.class)
        }
    )
    @RequestMapping(
        value = "/launch/",
        produces = {"application/json"},
        method = RequestMethod.GET
    )
    ResponseEntity<Launch> getLaunch(@RequestHeader HttpHeaders headers);



}
