package com.nexign.hrs.model;

import lombok.*;

import java.time.Duration;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CallCostCalculatedEvent {

    private long accountId;
    private CallType callType;
    private double cost;
    private Date startDate;
    private Date endDate;
    private Duration duration;
}
