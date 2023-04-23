package com.nexign.tariff.model;

import com.nexign.tariff.entity.CallType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Data
public class TariffByParametersModel {

    private long tariffId;
    private CallType callType;
}
