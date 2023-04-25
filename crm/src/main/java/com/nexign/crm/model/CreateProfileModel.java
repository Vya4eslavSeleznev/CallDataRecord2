package com.nexign.crm.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CreateProfileModel {

    private String phoneNumber;
    private char[] password;
    private String role;
    private long tariffId;
}
