package com.nexign.brt.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table
@Getter
@Setter
public class AccountTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, name = "account_id")
    @ManyToOne
    private int accountId;

    @Column(nullable = false)
    private double amount;
}
