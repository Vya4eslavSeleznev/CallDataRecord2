package com.nexign.brt;

import com.nexign.brt.repository.AccountCallRepository;
import com.nexign.brt.repository.AccountRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Date;

@SpringBootApplication
public class BrtApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(BrtApplication.class, args);
//		var a = context.getBean(AccountCallRepository.class).findByAccountIdAndDate(7, new Date(2023, 4, 1), new Date(2023, 4, 30));
//		System.out.println(a);
	}

}
