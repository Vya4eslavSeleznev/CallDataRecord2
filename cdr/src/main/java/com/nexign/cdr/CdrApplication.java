package com.nexign.cdr;

import com.nexign.cdr.model.CallRecordModel;
import com.nexign.cdr.service.impl.CallDataRecordEventSenderImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Date;

@SpringBootApplication
public class CdrApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(CdrApplication.class, args);
		context.getBean(CallDataRecordEventSenderImpl.class).sendEvent(new CallRecordModel("44", "89110009988", new Date(), new Date()));
	}

}
