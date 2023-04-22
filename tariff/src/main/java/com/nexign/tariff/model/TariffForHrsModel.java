package com.nexign.tariff.model;

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
}
