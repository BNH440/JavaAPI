package com.blakehaug.java_api.user;

import com.blakehaug.java_api.security.TokenAuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class SecuredUsersController {
    @Autowired
    TokenAuthenticationService authentication;

    @GetMapping("/current") // returns the current user
    User getCurrent(@AuthenticationPrincipal final User user) {
        return user;
    }

    @GetMapping("/logout") // logs out the current user TODO not implemented
    boolean logout(@AuthenticationPrincipal final User user) {
        authentication.logout(user);
        return true;
    }
}
