package com.nexign.tariff.model;

import com.nexign.tariff.entity.TariffType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Data
public class TariffCostModel {

    private int interval;
    private double cost;
    private int currencyId;
    private TariffType tariffType;
}
