package com.blakehaug.java_api;

import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/public/users")
public class PublicUsersController {
    @NonNull
    UserAuthenticationService authentication;
    @NonNull
    UserCrudService users;

    @PostMapping("/register")
    String register(
            @RequestParam("username") final String username,
            @RequestParam("password") final String password) {
        users.save(
                new User(
                        username,
                        password,
                        new String[]{"ROLE_USER"})); // TODO fix roles
        )
    }
    )
}
