package com.blakehaug.java_api.security;

import com.blakehaug.java_api.user.User;
import com.blakehaug.java_api.user.UserAuthenticationService;
import com.blakehaug.java_api.user.UserService;
import com.google.common.collect.ImmutableMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class TokenAuthenticationService implements UserAuthenticationService {
    @Autowired
    JWTTokenService tokens;
    @Autowired
    UserService users;

    @Override
    public Optional<String> login(final String username, final String password) { // creates a token if the username and password are correct
        return users
                .findByUsername(username) // gets user from db
                .filter(user -> Objects.equals(password, user.getPassword())) // checks if the password is correct
                .map(user -> tokens.expiring(ImmutableMap.of("username", username))); // creates a token
    }

    @Override
    public Optional<User> findByToken(final String token){ // checks if the token is valid and returns the user
        return Optional
                .of(tokens.verify(token))
                .map(map -> map.get("username"))
                .flatMap(users::findByUsername);
    }

    @Override
    public void logout(User user) { // TODO: not implemented
    }
}
