package com.pearl.spring;

import org.apache.commons.lang.math.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@RestController
@RequestMapping("/rest")
public class RestResource {

	/*
	 * @Autowired RestTemplate restTemplate;
	 */

	@Value("${spring.health}")
	private String url;

	@HystrixCommand(fallbackMethod = "sayHelloFB", commandKey = "hello", groupKey = "hello")
	@GetMapping("/hello")
	public String sayHello() {

		if (RandomUtils.nextBoolean()) {

			throw new RuntimeException("Failed !");
		}
		return "hellow world!";

	}

	@HystrixCommand(fallbackMethod = "sayHelloFB", commandKey = "health", groupKey = "health")
	@GetMapping("/health")
	public String sayhealth() {

		RestTemplate restTemplate = new RestTemplate();

		return restTemplate.getForObject(url, String.class);

	}

	public String sayHelloFB() {
		return "Fall back Intialized";
	}
}
