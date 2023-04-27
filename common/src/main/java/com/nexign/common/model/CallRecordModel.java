package com.nexign.common.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
public class CallRecordModel implements Serializable {

    private CallType callType;
    private String phoneNumber;
    private Date startDate;
    private Date endDate;
}
