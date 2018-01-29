package com.projekat.kts;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

import com.projekat.kts.config.SwaggerConfig;

@SpringBootApplication
@Import(SwaggerConfig.class)
public class KtsApplication {

	public static void main(String[] args) {
		SpringApplication.run(KtsApplication.class, args);
	}
}
