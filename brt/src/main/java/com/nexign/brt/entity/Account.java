package com.nexign.brt.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "account")
public class Account {

    public Account(long userId, double balance) {
        this.userId = userId;
        this.balance = balance;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, name = "user_id")
    private long userId;

    @Column(nullable = false)
    private double balance;
}
