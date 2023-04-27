package com.nexign.user.security.jwt;

import com.nexign.common.model.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtTokenProvider {
    private JwtProperties jwtProperties;

    //@Qualifier("customUserDetailsService")
    //@Autowired
    //private UserDetailsService userDetailsService;

    private String secretKey;

    public JwtTokenProvider(JwtProperties jwtProperties) {//}, UserDetailsService userDetailsService) {
        this.jwtProperties = jwtProperties;
        //this.userDetailsService = userDetailsService;
    }

    public String createToken(String userName, Role role) {
        Claims claims = Jwts.claims().setSubject(userName);
        claims.put("role", role);

        Date now = new Date();
        Date validity = new Date(now.getTime() + jwtProperties.getValidityInMs());

        return Jwts.builder().setClaims(claims).setIssuedAt(now).setExpiration(validity).signWith(SignatureAlgorithm.HS256,
          secretKey).compact();
    }

    private String getUserName(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

    @PostConstruct
    private void init() {
        secretKey = Base64.getEncoder().encodeToString(jwtProperties.getSecretKey().getBytes());
    }
}
