package com.example.mayak;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class MayakApplication {

	public static void main(String[] args) {
		SpringApplication.run(MayakApplication.class, args);
	}

}
