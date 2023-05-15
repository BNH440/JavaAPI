package com.blakehaug.java_api;

import java.util.Optional;

public interface UserCrudService {
    User save(User user);

    Optional<User> find(Integer id);

    Optional<User> findByUsername(String username);
}
