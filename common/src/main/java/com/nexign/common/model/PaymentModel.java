package com.nexign.common.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PaymentModel {

    private String phoneNumber;
    private double amount;
}
