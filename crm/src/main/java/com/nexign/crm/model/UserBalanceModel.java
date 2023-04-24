package com.nexign.crm.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserBalanceModel {

    private long userId;
    private double balance;
}
