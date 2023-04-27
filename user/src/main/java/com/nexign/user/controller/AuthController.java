package com.nexign.user.controller;

import com.nexign.user.entity.UserCredential;
import com.nexign.user.exception.InvalidUserNameOrPasswordException;
import com.nexign.common.model.AuthRequestModel;
import com.nexign.user.security.jwt.JwtTokenProvider;
import com.nexign.user.service.impl.CustomUserDetailsService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {

    JwtTokenProvider jwtTokenProvider;
    CustomUserDetailsService userDetailsService;

    @PostMapping("/signin")
    public ResponseEntity<?> singIn(@RequestBody AuthRequestModel request) {
        try {
            UserCredential user = userDetailsService.getAuthenticatedUser(request.getUsername(), request.getPassword());
            String token = jwtTokenProvider.createToken(user.getUsername(), user.getRole());

            Map<Object, Object> model = new HashMap<>();
            model.put("token", token);

            return ResponseEntity.ok(model);
        }
        catch(InvalidUserNameOrPasswordException | AuthenticationException e) {
            throw new BadCredentialsException("Invalid username or password");
        }
    }
}
