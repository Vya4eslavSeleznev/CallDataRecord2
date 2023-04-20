package com.nexign.tariff.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "tariff_call_type")
public class TariffCallType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "tariff_id")
    private long tariffId;

    @Column(name = "call_type", nullable = false)
    private String callType;
}
