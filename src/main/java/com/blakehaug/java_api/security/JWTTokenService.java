package com.blakehaug.java_api.security;

import com.google.common.base.Supplier;
import com.google.common.collect.ImmutableMap;
import io.jsonwebtoken.*;
import io.jsonwebtoken.impl.compression.GzipCompressionCodec;
import io.jsonwebtoken.security.Keys;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;

// The JWTTokenservice class creates and validates JSON Web Tokens (JWT).
@Service
public class JWTTokenService implements Clock, TokenService {
    private static final GzipCompressionCodec COMPRESSION_CODEC = new GzipCompressionCodec();

    String issuer;
    int expirationSec;
    int clockSkewSec;
    SecretKey secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256); // creating a secret signing key for the tokens

    JWTTokenService(@Value("${jwt.issuer:blakehaug}") final String issuer,
                    @Value("${jwt.expiration-sec:86400}") final int expirationSec, // 1 day until the jwt expires
                    @Value("${jwt.clock-skew-sec:300}") final int clockSkewSec)
    {
        super();
        this.issuer = issuer;
        this.expirationSec = expirationSec;
        this.clockSkewSec = clockSkewSec;
    }

    @Override
    public String permanent(final Map<String, String> attributes) { // creates a non-expiring token
        return newToken(attributes, 0);
    }

    @Override
    public String expiring(final Map<String, String> attributes) { // creates a token that expires after expirationSec seconds
        return newToken(attributes, expirationSec);
    }

    private String newToken(final Map<String, String> attributes, final int expiresInSec){ // creates a new token signed with the secret key
        final DateTime now = DateTime.now();
        final Claims claims = Jwts
                .claims()
                .setIssuer(issuer)
                .setIssuedAt(now.toDate());

        if (expiresInSec > 0) {
            final DateTime expiresAt = now.plusSeconds(expiresInSec);
            claims.setExpiration(expiresAt.toDate());
        }
        claims.putAll(attributes);

        return Jwts
                .builder()
                .setClaims(claims)
                .signWith(secretKey)
                .compressWith(COMPRESSION_CODEC)
                .compact();
    }

    @Override // Verify that the token is still valid and was created by this server, then return the data of the user associated with the token
    public Map<String, String> verify(final String token) throws JwtException {
        final JwtParser parser = Jwts
                .parserBuilder()
                .requireIssuer(issuer)
                .setClock(this)
                .setAllowedClockSkewSeconds(clockSkewSec)
                .setSigningKey(Keys.hmacShaKeyFor(secretKey.getEncoded()))
                .build();

        if(parser.parseClaimsJws(token).getBody().getExpiration().before(new Date())) {
        	System.out.println("Token is expired");

            throw new JwtException("Token is expired");
        } else {
        	System.out.println("Token is valid");
        }

        return parseClaims(() -> parser.parseClaimsJws(token).getBody());
    }

    private static Map<String, String> parseClaims(final Supplier<Claims> toClaims) { // Parses the json data stored in the token and returns a map
        try {
            final Claims claims = toClaims.get();
            final ImmutableMap.Builder<String, String> builder = ImmutableMap.builder();
            for (final Map.Entry<String, Object> e: claims.entrySet()) {
                builder.put(e.getKey(), String.valueOf(e.getValue()));
            }
            return builder.build();
        } catch (final IllegalArgumentException | JwtException e) {
            return ImmutableMap.of();
        }
    }

    @Override
    public Date now() { // Returns the current time
        return DateTime.now().toDate();
    }
}
