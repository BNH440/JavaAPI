package com.blakehaug.java_api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

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
    public ResponseEntity<User> listOne(@PathVariable Integer id) {
        Optional<User> user = userService.find(id);

        if(user.isPresent()) {
            return ResponseEntity.ok(user.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/users/get/{email}")
    public ResponseEntity<User> listOne(@PathVariable String email) {
        Optional<User> user = userService.findByUsername(email);

        if(user.isPresent()) {
            return ResponseEntity.ok(user.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
