package org.core.boot.sample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Hello world!
 *
 */
@Configuration
@EnableAutoConfiguration
@ComponentScan
public class App {

	@Bean
	public AqsProperties aqsProperties() {
		return new AqsProperties();
	}

	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}
}
