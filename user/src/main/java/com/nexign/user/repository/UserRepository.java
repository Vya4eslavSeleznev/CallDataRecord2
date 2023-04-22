package com.nexign.user.repository;

import com.nexign.user.entity.UserCredential;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<UserCredential, Long> {
}
