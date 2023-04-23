package com.nexign.hrs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class HrsApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext c = SpringApplication.run(HrsApplication.class, args);
	}

}
