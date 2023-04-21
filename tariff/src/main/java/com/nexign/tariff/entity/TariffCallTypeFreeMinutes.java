package com.nexign.tariff.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "tariff_call_type_free_minutes")
@NoArgsConstructor
public class TariffCallTypeFreeMinutes {

    public TariffCallTypeFreeMinutes(TariffCallType tariffCallType, int minutes, double price, Currency currency) {
        this.tariffCallType = tariffCallType;
        this.minutes = minutes;
        this.price = price;
        this.currency = currency;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "tariff_call_type_id")
    private TariffCallType tariffCallType;

    @Column(nullable = false)
    private int minutes;

    @Column(nullable = false)
    private double price;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "currency_id")
    private Currency currency;
}
