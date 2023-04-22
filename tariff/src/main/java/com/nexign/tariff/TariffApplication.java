package com.nexign.tariff;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class TariffApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(TariffApplication.class, args);
	}

}
