package com.nexign.tariff.service.impl;

import com.nexign.tariff.entity.*;
import com.nexign.tariff.model.TariffModel;
import com.nexign.tariff.repository.TariffCallTypeCostRepository;
import com.nexign.tariff.repository.TariffCallTypeFreeMinutesRepository;
import com.nexign.tariff.service.TariffService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TariffServiceImpl implements TariffService {

    private TariffCallTypeCostRepository callTypeCostRepository;
    private TariffCallTypeFreeMinutesRepository callTypeFreeMinutesRepository;

    @Override
    public void saveTariff(TariffModel tariffModel) {
        Currency currency = new Currency(tariffModel.getCurrencyName(), tariffModel.getCurrencyShortName());
        TariffCallType tariffCallType = new TariffCallType(
          new Tariff(tariffModel.getTariffName()), tariffModel.getCallType()
        );

        if(tariffModel.getTariffMinutes() != 0) {
            TariffCallTypeFreeMinutes callTypeFreeMinutes = new TariffCallTypeFreeMinutes(
              tariffCallType,
              tariffModel.getTariffMinutes(),
              tariffModel.getTariffCost(),
              currency
            );

            callTypeFreeMinutesRepository.save(callTypeFreeMinutes);
        }

        TariffCallTypeCost callTypeCost = new TariffCallTypeCost(
          tariffCallType,
          tariffModel.getAboveTariffMinutes(),
          tariffModel.getAboveTariffCost(),
          currency
        );

        callTypeCostRepository.save(callTypeCost);
    }
}
