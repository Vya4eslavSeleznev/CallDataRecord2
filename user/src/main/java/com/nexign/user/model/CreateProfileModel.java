package com.nexign.user.model;

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
    private char[] password;
    private String role;
    private long tariffId;
}
