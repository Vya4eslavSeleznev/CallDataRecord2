package com.nexign.tariff.model;

import com.nexign.tariff.entity.CallType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class TariffByParametersModel {

    private long tariffId;
    private CallType callType;
}
