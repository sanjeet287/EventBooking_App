package com.eventbooking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.eventbooking"})
public class EventbookingServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(EventbookingServiceApplication.class, args);
	}

}
