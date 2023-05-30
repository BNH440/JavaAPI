package com.blakehaug.java_api.security;

public interface PasswordEncoder { // TODO: I didn't have time to add password encoding so far
    String encode(CharSequence rawPassword);

    boolean matches(CharSequence rawPassword, String encodedPassword);

    default boolean upgradeEncoding(String encodedPassword) {
        return false;
    }
}