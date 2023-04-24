package com.nexign.brt;

import com.nexign.brt.model.UserBalanceModel;
import com.nexign.brt.repository.AccountRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class BrtApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext c = SpringApplication.run(BrtApplication.class, args);

//		List<Long> ids = new ArrayList<>();
//		ids.add(7L);
//		ids.add(11L);
//		ids.add(8L);
//		ids.add(9L);
//		ids.add(10L);
//		ids.add(12L);
//		ids.add(15L);
//
//
//		List<UserBalanceModel> g = c.getBean(AccountRepository.class).findByUserIdIn(ids);
//


		System.out.println();
	}

}
