package com.nexign.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.persistence.EntityManager;

@SpringBootApplication
public class UserApplication {

	@Autowired
	private EntityManager entityManager;

	public static void main(String[] args) {
		SpringApplication.run(UserApplication.class, args);
	}

}
