package com.ll.feelko;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class FeelKoApplication {

	public static void main(String[] args) {
		SpringApplication.run(FeelKoApplication.class, args);
	}

}
