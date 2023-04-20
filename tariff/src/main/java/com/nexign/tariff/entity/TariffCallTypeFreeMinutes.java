package com.nexign.tariff.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "tariff_call_type_free_minutes")
public class TariffCallTypeFreeMinutes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "tariff_call_type_id")
    private long tariffCallTypeId;

    @Column(nullable = false)
    private int minutes;
}
