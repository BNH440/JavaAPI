package com.blakehaug.java_api.security;

import java.util.Map;

public interface TokenService {
    String permanent(Map<String, String> attributes);

    String expiring(Map<String, String> attributes);

    Map<String, String> verify(String token);
}
