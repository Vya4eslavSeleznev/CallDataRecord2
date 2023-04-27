package com.nexign.user.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class FindByPhoneModel {

    private long userId;
    private long tariffId;
}
