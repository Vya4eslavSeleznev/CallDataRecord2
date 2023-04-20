package com.nexign.user.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "user_credential")
@NoArgsConstructor
public class UserCredential {

    public UserCredential(String role, char[] password) {
        this.role = role;
        this.password = password;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    //@Enumerated(EnumType.STRING)
    private String role;

    @Column(nullable = false)
    private char[] password;
}
