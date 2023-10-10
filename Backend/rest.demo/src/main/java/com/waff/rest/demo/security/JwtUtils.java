package com.waff.rest.demo.security;

import java.security.Key;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.waff.rest.demo.model.DaoUserDetails;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

// Utility class for handling JSON Web Tokens (JWT) in the application.
@Component
public class JwtUtils {
  // json web token
  // erstellt eine nachricht auf der website falls der login nicht geklappt hat als json
  private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

  private String jwtSecret = "dsfdsfdsdslkjdfrtuecbc764i43fsdsze98fs87efs76wifs87doiufds87fdiu98grd9fd9fd";

  // Session exp. time
  private int jwtExpirationMs = 999;

  // Generates a JWT token for the authenticated user.
  public String generateJwtToken(Authentication authentication) {

    DaoUserDetails userPrincipal = (DaoUserDetails) authentication.getPrincipal();

    return Jwts.builder()
        .setSubject((userPrincipal.getUsername()))
        .setIssuedAt(new Date())
        .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
        .signWith(key(), SignatureAlgorithm.HS256)
        .compact();
  }
  
  private Key key() {
    return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
  }
  
  // Extracts the username from the given JWT token.
  public String getUserNameFromJwtToken(String token) {
    return Jwts.parserBuilder().setSigningKey(key()).build()
               .parseClaimsJws(token).getBody().getSubject();
  }
  // Validates the given JWT token.
  public boolean validateJwtToken(String authToken) {
    try {
      Jwts.parserBuilder().setSigningKey(key()).build().parse(authToken);
      return true;
    } catch (MalformedJwtException e) {
      logger.error("Invalid JWT token: {}", e.getMessage());
    } catch (ExpiredJwtException e) {
      logger.error("JWT token is expired: {}", e.getMessage());
    } catch (UnsupportedJwtException e) {
      logger.error("JWT token is unsupported: {}", e.getMessage());
    } catch (IllegalArgumentException e) {
      logger.error("JWT claims string is empty: {}", e.getMessage());
    }

    return false;
  }
}