package com.nexign.tariff.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "tariff_call_type")
@NoArgsConstructor
public class TariffCallType {

    public TariffCallType(Tariff tariff, String callType) {
        this.tariff = tariff;
        this.callType = callType;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "tariff_id")
    private Tariff tariff;

    @Column(name = "call_type", nullable = false)
    private String callType;
}
