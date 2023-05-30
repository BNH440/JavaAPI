package com.blakehaug.java_api.user;

import com.blakehaug.java_api.security.TokenAuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/public/users")
public class PublicUsersController { // Controls the endpoint "/public/users" which allows for login and signup
    @Autowired
    TokenAuthenticationService authentication;
    @Autowired
    UserService users;

    @PostMapping("/register")
    String register(
            @RequestParam("username") final String username,
            @RequestParam("password") final String password) {
        users.save( // saves a new user to the db with the credentials provided (conflicting usernames will throw an error automatically)
                new User(username,
                        username,
                        password)
        );
        return login(username, password);
    }

    @PostMapping("/login")
    String login(
            @RequestParam("username") final String username,
            @RequestParam("password") final String password) {
        return authentication
                .login(username, password)
                .orElseThrow(() -> new RuntimeException("invalid login and/or password")); // tries to log in using the credentials provided and throws an error if they are invalid
    }
}
