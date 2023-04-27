package com.nexign.hrs.service;

import com.nexign.common.model.CallType;
import com.nexign.common.model.TariffInfoModel;

import java.util.List;

public interface TariffGateway {

    List<TariffInfoModel> getTariffInfo(long tariffId, CallType callType);
}
