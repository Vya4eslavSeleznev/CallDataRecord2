package com.nexign.brt.model;

import com.nexign.brt.entity.AccountCall;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Data
public class UserCallsModel {

    private List<AccountCall> accountCallList;
    private double totalAmount;
}
