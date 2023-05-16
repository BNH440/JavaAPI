package com.blakehaug.java_api.user;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends CrudRepository<User, String> {
    @Query(value = "SELECT u FROM User u WHERE u.email = :email")
    public User getUserByUsername(@Param("email") String email);
}