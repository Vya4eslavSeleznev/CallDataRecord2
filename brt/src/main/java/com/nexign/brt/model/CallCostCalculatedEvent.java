package com.nexign.brt.model;

import com.nexign.brt.entity.CallType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.Duration;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@Data
public class CallCostCalculatedEvent {

    private long accountId;
    private CallType callType;
    private double cost;
    private Date startDate;
    private Date endDate;
    private Duration duration;
}
