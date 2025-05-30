package com.AuthService.config;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import java.util.Date;

@Component
public class JwtUtils {
  @Value("${jwt.secret}") private String jwtSecret;
  @Value("${jwt.expirationMs}") private int jwtExpirationMs;

  public String generateToken(User userPrincipal) {
    return Jwts.builder()
      .setSubject(userPrincipal.getUsername())
      .setIssuedAt(new Date())
      .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
      .signWith(SignatureAlgorithm.HS512, jwtSecret)
      .compact();
  }

  public String getUsernameFromJwt(String token) {
    return Jwts.parser()
      .setSigningKey(jwtSecret)
      .parseClaimsJws(token)
      .getBody()
      .getSubject();
  }

  public boolean validateJwtToken(String authToken) {
    try {
      Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
      return true;
    } catch (JwtException e) {
      // log different exceptions if needed
    }
    return false;
  }
}
