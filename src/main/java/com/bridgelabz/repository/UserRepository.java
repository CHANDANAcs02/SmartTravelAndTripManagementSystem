package com.bridgelabz.repository;

import com.bridgelabz.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Checks whether a user already exists with the given email
    boolean existsByEmail(String email);
}