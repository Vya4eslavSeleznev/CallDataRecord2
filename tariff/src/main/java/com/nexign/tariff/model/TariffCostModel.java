package com.nexign.tariff.model;

import com.nexign.common.model.TariffType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class TariffCostModel {

    private int interval;
    private double cost;
    private int currencyId;
    private TariffType tariffType;
}
