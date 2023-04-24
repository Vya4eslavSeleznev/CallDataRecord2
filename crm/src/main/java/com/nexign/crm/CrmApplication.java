package com.nexign.crm;

import com.nexign.crm.service.CrmService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class CrmApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext c = SpringApplication.run(CrmApplication.class, args);

		c.getBean(CrmService.class).runBilling();
	}

}
