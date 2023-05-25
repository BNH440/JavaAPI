package com.blakehaug.java_api.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    @Query(value = "SELECT r FROM Role r WHERE r.name = :name")
    public Role getRoleByName(@Param("name") String name);
}

