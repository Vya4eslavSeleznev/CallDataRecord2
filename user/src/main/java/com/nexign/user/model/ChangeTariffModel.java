package com.nexign.user.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ChangeTariffModel {

    private String phoneNumber;
    private long tariffId;
}
