package com.blakehaug.java_api.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements UserCrudService{
    @Autowired
    private UserRepository userRepository;

    @Override
    public User save(User user) { // saves the user to the db
        return userRepository.save(user);
    }

    @Override
    public Optional<User> find(String id) { // finds the user by id
        return userRepository.findById(id);
    }

    @Override
    public Optional<User> findByUsername(String username) { // finds the user by username
        User user = userRepository.getUserByUsername(username);

        if(user == null) {
            return Optional.empty();
        }

        return Optional.of(user);
    }
}
