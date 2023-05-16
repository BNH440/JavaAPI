package com.blakehaug.java_api.security;

import com.blakehaug.java_api.user.User;
import com.blakehaug.java_api.user.UserAuthenticationService;
import com.blakehaug.java_api.user.UserCrudService;
import com.blakehaug.java_api.user.UserService;
import com.google.common.collect.ImmutableMap;
import lombok.NonNull;
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
    public Optional<String> login(final String username, final String password) {
        return users
                .findByUsername(username)
                .filter(user -> Objects.equals(password, user.getPassword()))
                .map(user -> tokens.expiring(ImmutableMap.of("username", username)));
    }

    @Override
    public Optional<User> findByToken(final String token){
        return Optional
                .of(tokens.verify(token))
                .map(map -> map.get("username"))
                .flatMap(users::findByUsername);
    }

    @Override
    public void logout(User user) {
    }
}
