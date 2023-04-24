package com.nexign.crm.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Data
public class ChangeTariffResponseModel {

    private long id;
    private String phoneNumber;
    private long tariffId;
}