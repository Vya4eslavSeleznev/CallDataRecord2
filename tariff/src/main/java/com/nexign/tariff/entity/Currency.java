package com.nexign.tariff.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "currency")
@NoArgsConstructor
public class Currency {

    public Currency(String name, String shortName) {
        this.name = name;
        this.shortName = shortName;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String name;

    @Column(name = "short_name", nullable = false)
    private String shortName;
}
