package com.paypay.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.paypay.model.User;

@Repository
public interface UserRepo extends JpaRepository<User, Integer> {
    public User findByEmail(String email);
}
