package com.ableneo.meetups.frontend;

import co.elastic.apm.attach.ElasticApmAttacher;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FrontendApplication {

	public static void main(String[] args) {
		ElasticApmAttacher.attach();
		SpringApplication.run(FrontendApplication.class, args);
	}

}
