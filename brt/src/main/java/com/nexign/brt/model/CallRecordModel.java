package com.nexign.brt.model;

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
public class CallRecordModel {
    private String callType;
    private String phoneNumber;
    private Date startDate;
    private Date endDate;
}
