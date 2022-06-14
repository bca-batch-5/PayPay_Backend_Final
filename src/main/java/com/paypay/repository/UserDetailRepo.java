package com.paypay.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.paypay.model.User;
import com.paypay.model.UserDetail;

@Repository
public interface UserDetailRepo extends JpaRepository<UserDetail, Integer>{
    UserDetail findByUser(User userId);
}
