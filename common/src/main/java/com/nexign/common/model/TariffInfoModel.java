package com.nexign.common.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TariffInfoModel {

    private int interval;
    private double cost;
    private long currencyId;
    private TariffType tariffType;
    private CallType callType;
}
