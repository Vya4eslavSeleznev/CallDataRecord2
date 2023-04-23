package com.nexign.crm.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Data
public class CreateProfileModel {

    private String phoneNumber;
    private long tariffId;
    private char[] password;
    private double balance;
}
