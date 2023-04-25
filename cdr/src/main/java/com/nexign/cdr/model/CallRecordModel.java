package com.nexign.cdr.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
public class CallRecordModel implements Serializable {

    private String callType;
    private String phoneNumber;
    private Date startDate;
    private Date endDate;
}
