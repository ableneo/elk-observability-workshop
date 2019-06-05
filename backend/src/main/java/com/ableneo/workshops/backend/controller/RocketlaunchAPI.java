package com.ableneo.workshops.backend.controller;

import com.ableneo.workshops.backend.model.Launch;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Api(tags = "Rocket launch info service")
@RequestMapping(value = "/rocketlaunch", produces = {"application/json"})
public interface RocketlaunchAPI {

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
    )
    ResponseEntity<Launch> getLaunch(@PathVariable(value="id") Long id);

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
    ResponseEntity<Launch> getLaunch();



}
