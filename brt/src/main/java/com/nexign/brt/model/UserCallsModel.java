package com.nexign.brt.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class UserCallsModel {

    private List<AccountCallResponseModel> accountCallList;
    private double totalAmount;
}
