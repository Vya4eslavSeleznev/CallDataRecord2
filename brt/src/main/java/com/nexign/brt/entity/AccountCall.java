package com.nexign.brt.entity;

import com.nexign.common.model.CallType;
import io.hypersistence.utils.hibernate.type.interval.PostgreSQLIntervalType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.time.Duration;
import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "account_call")
@NoArgsConstructor
@TypeDef(typeClass = PostgreSQLIntervalType.class, defaultForType = Duration.class)
public class AccountCall {

    public AccountCall(Account account, CallType callType, Date startDate, Date endDate, Duration duration, double cost) {
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

    @Column(nullable = false, name = "call_type")
    @Enumerated(EnumType.STRING)
    private CallType callType;

    @Column(nullable = false, name = "start_date")
    private Date startDate;

    @Column(nullable = false, name = "end_date")
    private Date endDate;

    @Column(nullable = false)
    private Duration duration;

    @Column(nullable = false)
    private double cost;
}
