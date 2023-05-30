package com.blakehaug.java_api.user;

import javax.persistence.*;

@Entity
@Table(name = "roles")
public class Role { // Defines the structure of a user role in the database
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}