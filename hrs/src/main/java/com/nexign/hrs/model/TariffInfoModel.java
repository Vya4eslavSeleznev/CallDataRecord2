package com.nexign.hrs.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class TariffInfoModel {

    private int interval;
    private double cost;
    private long currencyId;
    private TariffType tariffType;
}
