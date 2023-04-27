package com.nexign.crm.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ChangeTariffResponseModel {

    private long id;
    private String phoneNumber;
    private long tariffId;
}
