package com.bharath.springweb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class ExploringSpringWebAndSpringBootApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExploringSpringWebAndSpringBootApplication.class, args);
	}

}
