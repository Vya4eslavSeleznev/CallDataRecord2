package com.nexign.crm.security.jwt;

import com.nexign.common.exception.InvalidJwtAuthenticationException;
import com.nexign.common.model.Role;
import com.nexign.crm.model.UserPrincipalModel;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@Component
public class JwtTokenProvider {

    private JwtProperties jwtProperties;
    private String secretKey;

    public JwtTokenProvider(JwtProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
    }

    private String getUserName(String token) {
        return Jwts
          .parser()
          .setSigningKey(secretKey)
          .parseClaimsJws(token)
          .getBody()
          .getSubject();
    }

    private String getUserRole(String token) {
        return Jwts
          .parser()
          .setSigningKey(secretKey)
          .parseClaimsJws(token)
          .getBody()
          .get("role")
          .toString();
    }

    public boolean validateToken(String token) throws InvalidJwtAuthenticationException {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);

            return !claims.getBody().getExpiration().before(new Date());
        }
        catch(JwtException | IllegalArgumentException e) {
            throw new InvalidJwtAuthenticationException();
        }
    }

    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");

        if(bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }

        return null;
    }

    public Authentication getAuthentication(String token) {
        UserPrincipalModel userPrincipalModel = new UserPrincipalModel(getUserName(token), List.of(getUserRole(token)));

        return new UsernamePasswordAuthenticationToken(userPrincipalModel, "", userPrincipalModel.getAuthorities());
    }

    @PostConstruct
    private void init() {
        secretKey = Base64.getEncoder().encodeToString(jwtProperties.getSecretKey().getBytes());
    }
}
