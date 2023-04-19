package com.nexign.brt.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table
@Getter
@Setter
public class AccountCall {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, name = "account_id")
    @ManyToOne
    private int accountId;

    @Column(nullable = false)
    private String callType;

    @Column(nullable = false, name = "start_date")
    private Date startDate;

    @Column(nullable = false, name = "endDate")
    private Date endDate;

    @Column(nullable = false)
    private double cost;
}
