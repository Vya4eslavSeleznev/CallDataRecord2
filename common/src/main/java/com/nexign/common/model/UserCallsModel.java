package com.nexign.common.model;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserCallsModel {

    private List<AccountCallResponseModel> accountCallList;
    private double totalAmount;
}
