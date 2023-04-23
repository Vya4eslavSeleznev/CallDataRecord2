package com.nexign.hrs.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@Data
public class CallAuthorizedModel implements Serializable {

    private CallType callType;
    private long accountId;
    private long tariffId;
    private Date startDate;
    private Date endDate;
    private int minutesSpent;
}
