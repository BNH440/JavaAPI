package com.blakehaug.java_api.security;

import org.springframework.security.web.RedirectStrategy;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

class NoRedirectStrategy implements RedirectStrategy {

    @Override // Overrides the redirect behavior of spring security so that instead of redirecting to a login page (which makes no sense for an api), it does nothing and sends an error code
    public void sendRedirect(final HttpServletRequest request, final HttpServletResponse response, final String url) throws IOException {
    }
}

