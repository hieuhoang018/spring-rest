package com.hieuhoang.springrest.auth;

import com.hieuhoang.springrest.user.AppUser;
import com.hieuhoang.springrest.user.Role;
import com.hieuhoang.springrest.user.UserRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;


import java.util.Map;
import java.util.Collections;
import java.util.HashMap;


@RestController
@RequestMapping("/api/auth")
public class AuthController {


    private final UserRepository users;
    private final PasswordEncoder encoder;
    private final JwtService jwt;


    public AuthController(UserRepository users, PasswordEncoder encoder, JwtService jwt) {
        this.users = users;
        this.encoder = encoder;
        this.jwt = jwt;
    }


    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/register")
    public void register(@Valid @RequestBody AuthRequests.RegisterRequest req) {
        if (users.findByEmail(req.email).isPresent()) {
            throw new IllegalArgumentException("Email already registered");
        }
    AppUser user = new AppUser(
                req.name,
                req.email,
                encoder.encode(req.password),
        Collections.singleton(Role.USER)
        );
        users.save(user);
    }


    @PostMapping("/login")
    public AuthResponse login(@Valid @RequestBody AuthRequests.LoginRequest req) {
    AppUser user = users.findByEmail(req.email)
                .orElseThrow(() -> new IllegalArgumentException("Invalid credentials"));
        if (!encoder.matches(req.password, user.getPassword())) {
            throw new IllegalArgumentException("Invalid credentials");
        }
    Map<String,Object> claims = new HashMap<>();
    claims.put("name", user.getName());
    claims.put("roles", user.getRoles());
    String token = jwt.generateToken(user.getEmail(), claims);
        return new AuthResponse(token);
    }
}
