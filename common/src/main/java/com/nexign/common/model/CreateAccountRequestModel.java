package com.nexign.common.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CreateAccountRequestModel {

    private long userId;
    private double balance;
}
