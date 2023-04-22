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
public class TariffModel {

    private String tariffName;
    private List<TariffCallTypeModel> tariffCallTypeModels;
}
