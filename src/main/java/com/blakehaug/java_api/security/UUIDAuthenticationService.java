package com.blakehaug.java_api.security;

import com.blakehaug.java_api.user.User;
import com.blakehaug.java_api.user.UserAuthenticationService;
import com.blakehaug.java_api.user.UserCrudService;
import com.blakehaug.java_api.user.UserService;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UUIDAuthenticationService implements UserAuthenticationService {
    @Autowired
    UserService users;

    @Override
    public Optional<String> login(final String username, final String password) {
        final String uuid = UUID.randomUUID().toString();
        final User user = new User(uuid, username, password);
        users.save(user);
        return Optional.of(uuid);
    }
    @Override
    public Optional<User> findByToken(String token) {
        return users.find(token);
    }

    @Override
    public void logout(User user) {

    }
}
