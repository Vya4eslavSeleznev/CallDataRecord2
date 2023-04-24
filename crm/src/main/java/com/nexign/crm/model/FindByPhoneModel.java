package com.nexign.crm.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@Data
@NoArgsConstructor
public class FindByPhoneModel {

    private long userId;
    private long tariffId;
}
