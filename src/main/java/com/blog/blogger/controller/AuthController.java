package com.blog.blogger.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import com.blog.blogger.security.JwtAuthRequest;
import com.blog.blogger.security.JwtAuthResponse;
import com.blog.blogger.security.JwtHelper;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtHelper jwtHelper;

    @Autowired
    private UserDetailsService userDetailsService;

    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponse> login(@RequestBody JwtAuthRequest request) {

        // authenticate email + password
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        // load user and generate token
        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());
        String token = jwtHelper.generateToken(userDetails.getUsername());

        JwtAuthResponse response = new JwtAuthResponse();
        response.setToken(token);

        return ResponseEntity.ok(response);
    }
}
