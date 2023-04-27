package com.nexign.crm.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class BillingModel {

    private List<PhoneAndBalanceModel> numbers;
}
