package com.nexign.user.model;

import com.nexign.user.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CreateProfileModel {

    private String phoneNumber;
    private char[] password;
    private Role role;
    private long tariffId;
}
