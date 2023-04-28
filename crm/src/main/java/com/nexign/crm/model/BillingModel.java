package com.nexign.crm.model;

import com.nexign.common.model.PhoneAndBalanceModel;
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
