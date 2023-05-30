package com.blakehaug.java_api.student;

import com.blakehaug.java_api.user.User;

import javax.persistence.*;

@Entity
@Table(name = "students")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; // the id (primary key) is generated automatically (in sequential order)
    private String name;
    private String email;

    @OneToOne(mappedBy = "student")
    private User user; // one user is associated with each student

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString(){
        return "Student{" + "id=" + id + ", name=" + name + ", email=" + email + '}';
    }
}