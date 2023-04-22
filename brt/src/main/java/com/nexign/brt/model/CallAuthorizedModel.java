package com.nexign.brt.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.Duration;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@Data
public class CallAuthorizedModel implements Serializable {

    private String callType;
    private long accountId;
    private long tariffId;
    private Date startDate;
    private Date endDate;
    private long minutesSpent;
}
