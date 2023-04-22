package com.nexign.user.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "customer")
@NoArgsConstructor
public class Customer {

    public Customer(UserCredential userCredential, String phoneNumber, long tariffId) {
        this.userCredential = userCredential;
        this.phoneNumber = phoneNumber;
        this.tariffId = tariffId;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private UserCredential userCredential;

    @Column(name = "phone", nullable = false, unique = true)
    private String phoneNumber;

    @Column(name = "tariff_id", nullable = false)
    private long tariffId;
}
