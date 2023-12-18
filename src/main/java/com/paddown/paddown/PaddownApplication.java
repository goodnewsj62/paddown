package com.paddown.paddown;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(ConfigProperties.class)
public class PaddownApplication {

	public static void main(String[] args) {
		SpringApplication.run(PaddownApplication.class, args);
	}

}
