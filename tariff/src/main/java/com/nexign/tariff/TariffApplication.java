package com.nexign.tariff;

import com.nexign.tariff.entity.TariffCallType;
import com.nexign.tariff.model.TariffModel;
import com.nexign.tariff.repository.TariffCallTypeRepository;
import com.nexign.tariff.service.TariffService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.List;

@SpringBootApplication
public class TariffApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(TariffApplication.class, args);
		TariffModel m  = context.getBean(TariffService.class).getTariffInfo(9L);
		System.out.println();
	}

}
