package com.nexign.tariff.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "tariff_call_type_cost")
public class TariffCallTypeCost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "tariff_call_type_id")
    private long tariffCallTypeId;

    @Column(name = "tariffication_interval", nullable = false)
    private int tarifficationInterval;

    @Column(nullable = false)
    private double price;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "currency_id")
    private long currencyId;
}
