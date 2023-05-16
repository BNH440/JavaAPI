package com.blakehaug.java_api.user;

import java.util.Optional;

public interface UserCrudService {
    User save(User user);

    Optional<User> find(String id);

    Optional<User> findByUsername(String username);
}
