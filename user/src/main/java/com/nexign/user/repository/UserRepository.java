package com.nexign.user.repository;

import com.nexign.user.entity.UserCredential;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<UserCredential, Long> {

    Optional<UserCredential> findUserCredentialByUsername(String username);
}
