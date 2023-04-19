package com.nexign.user.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table
@Getter
@Setter
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, name = "user_id")
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private int userId;

    @Column(nullable = false)
    private String phoneNumber;

    @Column(nullable = false)
    private int tariffId;

    @Column(nullable = false)
    private int accountId;
}
