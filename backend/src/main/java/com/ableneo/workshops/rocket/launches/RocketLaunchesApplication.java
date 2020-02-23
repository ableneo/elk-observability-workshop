package com.ableneo.workshops.rocket.launches;

import co.elastic.apm.attach.ElasticApmAttacher;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RocketLaunchesApplication {

    public static void main(String[] args) {
        ElasticApmAttacher.attach();
        SpringApplication.run(RocketLaunchesApplication.class, args);
    }

}
