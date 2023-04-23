package com.nexign.brt.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Data
public class PaymentModel {

    private String phoneNumber;
    private double amount;
}
