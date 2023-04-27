package com.nexign.tariff.entity;

import com.nexign.common.model.TariffType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "tariff_call_type_cost")
@NoArgsConstructor
public class TariffCallTypeCost {

    public TariffCallTypeCost(TariffCallType tariffCallType, int tarifficationInterval, double price, long currencyId,
                              TariffType tariffType) {
        this.tariffCallType = tariffCallType;
        this.tarifficationInterval = tarifficationInterval;
        this.price = price;
        this.currencyId = currencyId;
        this.tariffType = tariffType;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "tariff_call_type_id")
    private TariffCallType tariffCallType;

    @Column(name = "tariffication_interval", nullable = false)
    private int tarifficationInterval;

    @Column(nullable = false)
    private double price;

    private long currencyId;

    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING)
    private TariffType tariffType;
}
