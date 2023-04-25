package com.nexign.brt.model;

import com.nexign.brt.entity.CallType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.Duration;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
public class AccountCallResponseModel {

    private CallType callType;
    private Date startTime;
    private Date endTime;
    private Duration duration;
    private double cost;
}
