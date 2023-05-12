package com.blakehaug.java_api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @Autowired
    private UserDetailsServiceImpl userDetailsRepository;

//    @GetMapping("/users")
//    public ResponseEntity<List<User>> listAll() {
//        List<User> users = userRepository.findAll();
//
//        if (users.isEmpty()) {
//            return ResponseEntity.notFound().build();
//        } else {
//            return ResponseEntity.ok(users);
//        }
//    }

    @GetMapping("/users/get/{id}")
    public ResponseEntity<DBUserDetails> listOne(@PathVariable Integer id) {
        DBUserDetails user = userDetailsRepository.findByID(id);

        if(user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/users/get/{email}")
    public ResponseEntity<DBUserDetails> listOne(@PathVariable String email) {
        DBUserDetails user = userDetailsRepository.loadUserByUsername(email);

        if(user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
