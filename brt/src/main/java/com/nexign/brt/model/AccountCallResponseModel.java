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
public class AccountCallResponseModel {

    private CallType callType;
    private Date startTime;
    private Date endTime;
    private Duration duration;
    private double cost;
}
