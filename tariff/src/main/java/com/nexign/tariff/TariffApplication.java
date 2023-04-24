package com.nexign.tariff;

import com.nexign.tariff.entity.TariffCallTypeCost;
import com.nexign.tariff.repository.TariffCallTypeCostRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.List;

@SpringBootApplication
public class TariffApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext c = SpringApplication.run(TariffApplication.class, args);
//		List<TariffCallTypeCost> b = c.getBean(TariffCallTypeCostRepository.class).findByTariffCallTypeCost_TariffCallType_Tariff(12L);
//		System.out.println();
	}

}
