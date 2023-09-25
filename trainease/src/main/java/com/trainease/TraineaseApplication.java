package com.trainease;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class TraineaseApplication {

	public static void main(String[] args) {
		SpringApplication.run(TraineaseApplication.class, args);
	}

}
