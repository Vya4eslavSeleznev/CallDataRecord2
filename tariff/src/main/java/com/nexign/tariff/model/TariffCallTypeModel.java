package com.nexign.tariff.model;

import com.nexign.common.model.CallType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class TariffCallTypeModel {

    private CallType callType;
    private List<TariffCostModel> tariffCostModels;
}
