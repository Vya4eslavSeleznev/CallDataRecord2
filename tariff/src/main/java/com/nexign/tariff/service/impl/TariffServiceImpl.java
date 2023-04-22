package com.nexign.tariff.service.impl;

import com.nexign.tariff.entity.Tariff;
import com.nexign.tariff.entity.TariffCallType;
import com.nexign.tariff.entity.TariffCallTypeCost;
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
                  new TariffCallTypeCost(tcp, costModel.getInterval(), costModel.getCost(), costModel.getCurrencyId())
                );
            }
        }
    }

    @Override
    public TariffModel getTariffInfo(long tariffId) {
        List<TariffCallType> callTypeList = callTypeRepository.findByTariffId(tariffId);
        List<TariffCallTypeCost> callTypeCostList = new ArrayList<>();

        for(TariffCallType callType : callTypeList) {
            callTypeCostList.add(
              callTypeCostRepository.findByTariffCallTypeId(callType.getId())
            );
        }

        List<TariffCallTypeModel> tariffCallTypeModels = new ArrayList<>();

        for(TariffCallTypeCost callTypeCost : callTypeCostList) {


        }







        //TariffModel tariffModel = new TariffModel(tariff.getName(), );




        return null;
    }
}
