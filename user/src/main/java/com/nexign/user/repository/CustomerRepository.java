package com.nexign.user.repository;

import com.nexign.user.entity.Customer;
import com.nexign.user.model.UserPhoneNumberInterface;
import com.nexign.user.model.UserPhoneNumberModel;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface CustomerRepository extends CrudRepository<Customer, Long> {

    Customer findByPhoneNumber(String phoneNumber);

    //@Query("select new com.nexign.user.model.UserPhoneNumberModel(userCredential, phone) from Customer where userCredential in :ids")
    //List<UserPhoneNumberModel> findByUserIds(@Param("ids") List<Long> userIds);




//    "select new package.DeadlineType(a.id, a.code) from ABDeadlineType a "
//      "select user_id, phone from customer where user_id in (?1)"

//    @Query(value = "select new com.nexign.user.model.UserPhoneNumberModel(u.id, c.phone) from Customer c join UserCredential u on c.userCredential = u.id where u.id in (:userIds)")
//    List<UserPhoneNumberModel> findByUserIdIn(@Param("userIds") Collection<Long> userIds);


//    @Query(value = "select new com.nexign.user.model.UserPhoneNumberModel(c.id, c.phoneNumber) from Customer c where c.id in (:userIds)")
//    List<UserPhoneNumberModel> findByUserIdIn(@Param("userIds") Collection<Long> userIds);



    @Query(value = "select c from Customer c join UserCredential u on c.userCredential = u.id where u.id in (:userIds)")
    List<Customer> findByUserIds(@Param("userIds") Collection<Long> userIds);





}
