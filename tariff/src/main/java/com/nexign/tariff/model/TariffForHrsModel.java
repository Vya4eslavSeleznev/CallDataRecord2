package com.nexign.tariff.model;

import com.nexign.tariff.entity.CallType;
import com.nexign.tariff.entity.TariffType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Data
public class TariffForHrsModel {

    private int interval;
    private double cost;
    private long currencyId;
    private TariffType tariffType;
    private CallType callType;
}
