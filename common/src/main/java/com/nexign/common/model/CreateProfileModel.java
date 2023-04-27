package com.nexign.common.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CreateProfileModel {

    private String phoneNumber;
    private String password;
    private String username;
    private Role role;
    private long tariffId;
}
