package com.nexign.common.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateProfileModel extends UserCredentialModel {

    private String phoneNumber;
    private long tariffId;

    public CreateProfileModel(String phoneNumber, String password, String username, Role role, long tariffId) {
        super(password, username, role);
        this.phoneNumber = phoneNumber;
        this.tariffId = tariffId;
    }
}
