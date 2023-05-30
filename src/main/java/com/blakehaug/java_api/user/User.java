package com.blakehaug.java_api.user;

import com.blakehaug.java_api.student.Student;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "users")
public class User implements UserDetails { // Defines the structure of a user in the database
    @Id
    private String id; // matches username in my implementation

    @Column(nullable = false)
    private String username;

    @Column(nullable = true)
    private String name;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private int enabled;

    @OneToOne(cascade = CascadeType.ALL) // sets up the sql relationship between user and student
    @JoinColumn(name = "student_id", referencedColumnName = "id")
    private Student student; // each user can be assigned to a single student profile

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
        name = "users_roles",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "role_id")
        )
    private Set<Role> roles = new HashSet<>(); // each user can have multiple roles and each role can be assigned to multiple users

    public User(String id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.enabled = 1;
    }

    public User(String id, String username, String password, Student student) { // TODO implement
        this.id = id;
        this.username = username;
        this.password = password;
        this.student = student;
    }

    public User() {

    }

    public String getId() {
        return id;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    @Override
    public boolean isEnabled() {
        return enabled == 1;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<Role> roles = getRoles();
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();

        for (Role role : roles) {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        }

        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    public boolean isAdmin() {
        return getAuthorities().contains(new SimpleGrantedAuthority("ADMIN"));
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }
}
