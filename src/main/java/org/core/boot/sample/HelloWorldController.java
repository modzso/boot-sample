package org.core.boot.sample;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {

	@Autowired
	private AqsProperties props;

	@Value("${name}")
	private String name = "World";

	@Value("${greet}")
	private String greet = "Hello";

	@RequestMapping("/")
	public String hello() {

		return props + String.format("%s %s!", greet, name);
	}
}
