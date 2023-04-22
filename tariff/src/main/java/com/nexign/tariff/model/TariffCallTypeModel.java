package com.nexign.tariff.model;

import com.nexign.tariff.entity.CallType;
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

    private CallType callType;
    private List<TariffCostModel> tariffCostModels;
}
