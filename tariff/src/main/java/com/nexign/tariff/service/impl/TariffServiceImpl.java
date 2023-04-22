package com.nexign.tariff.service.impl;

import com.nexign.tariff.entity.CallType;
import com.nexign.tariff.entity.Tariff;
import com.nexign.tariff.entity.TariffCallType;
import com.nexign.tariff.entity.TariffCallTypeCost;
import com.nexign.tariff.exception.TariffNotFoundException;
import com.nexign.tariff.model.*;
import com.nexign.tariff.repository.TariffCallTypeCostRepository;
import com.nexign.tariff.repository.TariffCallTypeRepository;
import com.nexign.tariff.repository.TariffRepository;
import com.nexign.tariff.service.TariffService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class TariffServiceImpl implements TariffService {

    private TariffRepository tariffRepository;
    private TariffCallTypeRepository callTypeRepository;
    private TariffCallTypeCostRepository callTypeCostRepository;

    @Override
    public void saveTariff(TariffModel tariffModel) {
        List<TariffCallTypeModel> callTypeModels = tariffModel.getTariffCallTypeModels();
        Tariff tariff = new Tariff(tariffModel.getTariffName());

        for(TariffCallTypeModel callType : callTypeModels) {
            List<TariffCostModel> costModels = callType.getTariffCostModels();

            TariffCallType tcp = new TariffCallType(
              tariff,
              callType.getCallType()
            );

            for(TariffCostModel costModel : costModels) {
                callTypeCostRepository.save(
                  new TariffCallTypeCost(tcp, costModel.getInterval(), costModel.getCost(),
                    costModel.getCurrencyId(), costModel.getTariffType())
                );
            }
        }
    }

    @Override
    public List<TariffForHrsModel> getTariffInfo(TariffByParametersModel tariffByParametersModel) throws TariffNotFoundException {
        TariffCallType tariffCallType = callTypeRepository.findByTariffIdAndCallType(
          tariffByParametersModel.getTariffId(), tariffByParametersModel.getCallType());

        if(tariffCallType == null) {
            throw new TariffNotFoundException("Tariff not found");
        }

        List<TariffCallTypeCost> tariffCallTypeCosts = callTypeCostRepository.findByTariffCallTypeId(tariffCallType.getId());

        List<TariffForHrsModel> modelList = new ArrayList<>();

        for(TariffCallTypeCost tariffCallTypeCost : tariffCallTypeCosts) {
            modelList.add(
              new TariffForHrsModel(
                tariffCallTypeCost.getTarifficationInterval(),
                tariffCallTypeCost.getPrice(),
                tariffCallTypeCost.getCurrencyId(),
                tariffCallTypeCost.getTariffType())
              );
        }

        return modelList;
    }
}
