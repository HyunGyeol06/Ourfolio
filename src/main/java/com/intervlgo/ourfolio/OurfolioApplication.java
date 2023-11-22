package com.intervlgo.ourfolio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class OurfolioApplication {

	public static void main(String[] args) {
		SpringApplication.run(OurfolioApplication.class, args);
	}

}
