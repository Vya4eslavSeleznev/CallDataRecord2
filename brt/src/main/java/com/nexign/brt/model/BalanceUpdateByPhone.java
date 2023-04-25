package com.nexign.brt.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class BalanceUpdateByPhone {

    private String phoneNumber;
    private double amount;
}
