package com.eatthefrog.EventTemplateService;

import org.bson.codecs.ObjectIdGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class EventTemplateServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(EventTemplateServiceApplication.class, args);
	}

	@Bean
	public ObjectIdGenerator objectIdGenerator() {
		return new ObjectIdGenerator();
	}
}
