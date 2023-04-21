package com.nexign.tariff.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Data
public class TariffModel {

    private String tariffName;
    private String callType;
    private int tariffMinutes;
    private double tariffCost;
    private int aboveTariffMinutes;
    private double aboveTariffCost;
    private String currencyName;
    private String currencyShortName;
}
