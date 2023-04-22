package com.nexign.brt.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "account_call")
@NoArgsConstructor
public class AccountCall {

    public AccountCall(Account account, CallType callType, Date startDate, Date endDate, Date duration, double cost) {
        this.account = account;
        this.callType = callType;
        this.startDate = startDate;
        this.endDate = endDate;
        this.duration = duration;
        this.cost = cost;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "account_id")
    private Account account;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private CallType callType;

    @Column(nullable = false, name = "start_date")
    private Date startDate;

    @Column(nullable = false, name = "end_date")
    private Date endDate;

    @Column(nullable = false)
    private Date duration;

    @Column(nullable = false)
    private double cost;
}
