package com.nexign.crm.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Data
public class CreateCustomerModel {

    private String phoneNumber;
    private char[] password;
    private long tariffId;
    private double balance;
}
