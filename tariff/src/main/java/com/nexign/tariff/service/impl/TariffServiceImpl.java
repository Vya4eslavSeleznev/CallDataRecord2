package com.nexign.tariff.service.impl;

import com.nexign.common.model.CallType;
import com.nexign.common.model.TariffInfoModel;
import com.nexign.tariff.entity.Tariff;
import com.nexign.tariff.entity.TariffCallType;
import com.nexign.tariff.entity.TariffCallTypeCost;
import com.nexign.tariff.exception.TariffNotFoundException;
import com.nexign.tariff.model.TariffCallTypeModel;
import com.nexign.tariff.model.TariffCostModel;
import com.nexign.tariff.model.TariffModel;
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

    private TariffCallTypeRepository callTypeRepository;
    private TariffCallTypeCostRepository callTypeCostRepository;
    private TariffRepository tariffRepository;

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
    public List<TariffInfoModel> getTariffInfo(long tariffId, CallType callType) throws TariffNotFoundException {
        TariffCallType tariffCallType = callTypeRepository.findByTariffIdAndCallType(tariffId, callType);

        if(tariffCallType == null) {
            throw new TariffNotFoundException();
        }

        List<TariffCallTypeCost> tariffCallTypeCosts = callTypeCostRepository.findByTariffCallTypeId(tariffCallType.getId());

        List<TariffInfoModel> modelList = new ArrayList<>();

        for(TariffCallTypeCost tariffCallTypeCost : tariffCallTypeCosts) {
            modelList.add(
              new TariffInfoModel(
                tariffCallTypeCost.getTarifficationInterval(),
                tariffCallTypeCost.getPrice(),
                tariffCallTypeCost.getCurrencyId(),
                tariffCallTypeCost.getTariffType(),
                tariffCallTypeCost.getTariffCallType().getCallType()
              ));
        }

        return modelList;
    }

    @Override
    public String getCurrencyByTariffId(long tariffId) {
        return tariffRepository.findCurrencyByTariffId(tariffId);
    }
}
