package com.nexign.tariff.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Data
public class TariffCallTypeModel {

    private String callType;
    private List<TariffCostModel> tariffCostModels;
}
