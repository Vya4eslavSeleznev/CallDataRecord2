package com.nexign.hrs.service;

import com.nexign.hrs.model.CallType;
import com.nexign.hrs.model.TariffInfoModel;

import java.util.List;

public interface TariffGateway {

    List<TariffInfoModel> getTariffInfo(long tariffId, CallType callType);
}
