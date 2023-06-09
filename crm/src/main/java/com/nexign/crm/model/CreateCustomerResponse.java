package com.nexign.crm.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CreateCustomerResponse {

    private String phoneNumber;
    private long tariffId;
    private double balance;
}
