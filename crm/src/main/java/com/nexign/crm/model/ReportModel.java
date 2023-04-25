package com.nexign.crm.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class ReportModel {

    private long id;
    private String phoneNumber;
    private long tariffIndex;
    private List<AccountCallResponseModel> payload;
    private double totalCost;
    private String monetaryUnit;
}
