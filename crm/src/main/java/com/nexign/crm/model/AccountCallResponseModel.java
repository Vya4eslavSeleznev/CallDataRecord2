package com.nexign.crm.model;

import lombok.*;

import java.time.Duration;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AccountCallResponseModel {

    private CallType callType;
    private Date startTime;
    private Date endTime;
    private Duration duration;
    private double cost;
}
