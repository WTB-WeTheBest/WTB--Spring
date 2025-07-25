package org.gaung.wethebest.wtbspring.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;

import java.util.UUID;

public interface JwtUtil {

    Claims decodeToken(String token) throws JwtException;

    UUID getId(Claims payload);

    String getRole(Claims payload);
}