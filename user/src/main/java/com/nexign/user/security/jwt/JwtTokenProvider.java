package com.nexign.user.security.jwt;

import com.nexign.common.model.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtTokenProvider {

    private @Value("${validityInMs}") String validityInMs;
    private @Value("${secretKey}") String secretKey;

    public String createToken(String userName, Role role) {
        Claims claims = Jwts.claims().setSubject(userName);
        claims.put("role", role);

        Date now = new Date();
        Date validity = new Date(now.getTime() + Long.parseLong(validityInMs));

        return Jwts
          .builder()
          .setClaims(claims)
          .setIssuedAt(now)
          .setExpiration(validity)
          .signWith(SignatureAlgorithm.HS256, secretKey)
          .compact();
    }

    @PostConstruct
    private void init() {
        secretKey = Base64
          .getEncoder()
          .encodeToString(secretKey.getBytes());
    }
}
