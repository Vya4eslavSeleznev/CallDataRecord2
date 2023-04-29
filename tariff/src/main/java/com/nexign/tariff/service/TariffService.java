package com.nexign.tariff.service;

import com.nexign.common.model.CallType;
import com.nexign.common.model.TariffInfoModel;
import com.nexign.tariff.exception.TariffNotFoundException;
import com.nexign.tariff.model.TariffModel;

import java.util.List;

public interface TariffService {

    void saveTariff(TariffModel tariffModel);
    List<TariffInfoModel> getTariffInfo(long tariffId, CallType callType) throws TariffNotFoundException;
    String getCurrencyByTariffId(long tariffId);
}
