package com.blakehaug.java_api;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends CrudRepository<User, Integer> {
    @Query(value = "SELECT u FROM User u WHERE u.email = :email")
    public User getUserByUsername(@Param("email") String email);
}