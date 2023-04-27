package com.nexign.crm.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CreateCustomerModel {

    private String phoneNumber;
    private String password;
    private String username;
    private long tariffId;
    private double balance;
}
