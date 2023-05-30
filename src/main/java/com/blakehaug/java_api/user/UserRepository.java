package com.blakehaug.java_api.user;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends CrudRepository<User, String> {
    @Query(value = "SELECT u FROM User u WHERE u.username = :username")
    public User getUserByUsername(@Param("username") String username); // uses the above sql query to find a user by username
}