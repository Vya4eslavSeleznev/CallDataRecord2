package com.nexign.brt;

import com.nexign.brt.entity.CallType;
import com.nexign.brt.exception.AccountNotFoundException;
import com.nexign.brt.exception.BalanceLessThanZeroException;
import com.nexign.brt.model.CallCostCalculatedEvent;
import com.nexign.brt.service.AccountCallService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Date;

@SpringBootApplication
public class BrtApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext c = SpringApplication.run(BrtApplication.class, args);
//		try {
//			c.getBean(AccountCallService.class).addCall(
//			new CallCostCalculatedEvent(6L, CallType.OUTPUT, 51, new Date(2023, 04, 21), new Date(2023, 04, 22))
//			);
//		}
//		catch(BalanceLessThanZeroException e) {
//			e.printStackTrace();
//		}
//		catch(AccountNotFoundException e) {
//			e.printStackTrace();
//		}
	}

}
