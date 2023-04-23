package com.nexign.user.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Data
public class ChangeTariffModel {

    private String phoneNumber;
    private long tariffId;
}
