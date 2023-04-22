package com.nexign.tariff.service;

import com.nexign.tariff.entity.CallType;
import com.nexign.tariff.exception.TariffNotFoundException;
import com.nexign.tariff.model.TariffByParametersModel;
import com.nexign.tariff.model.TariffForHrsModel;
import com.nexign.tariff.model.TariffModel;

import java.util.List;

public interface TariffService {

    void saveTariff(TariffModel tariffModel);
    List<TariffForHrsModel> getTariffInfo(TariffByParametersModel tariffByParametersModel) throws TariffNotFoundException;
}
