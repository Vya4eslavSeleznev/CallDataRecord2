package com.nexign.common.model;

import lombok.*;

import java.time.Duration;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CallCostCalculatedEvent {

    private long accountId;
    private CallType callType;
    private double cost;
    private Date startDate;
    private Date endDate;
    private Duration duration;
}
