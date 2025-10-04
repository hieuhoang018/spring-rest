package com.hieuhoang.springrest.auth;

import com.hieuhoang.springrest.user.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;


import java.io.IOException;
import java.util.stream.Collectors;


@Component
public class JwtAuthFilter extends OncePerRequestFilter {


    private final JwtService jwt;
    private final UserRepository users;


    public JwtAuthFilter(JwtService jwt, UserRepository users) {
        this.jwt = jwt;
        this.users = users;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {


        String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (header != null && header.startsWith("Bearer ")) {
            String token = header.substring(7);
            try {
                String email = jwt.extractSubject(token);
                users.findByEmail(email).ifPresent(user -> {
                    if (jwt.isValid(token, email)) {
            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                                email,
                                null,
                                user.getRoles().stream()
                                        .map(r -> new SimpleGrantedAuthority("ROLE_" + r.name()))
                                        .collect(Collectors.toSet())
                        );
                        SecurityContextHolder.getContext().setAuthentication(auth);
                    }
                });
            } catch (Exception ignored) {
// invalid token → proceed unauthenticated
            }
        }
        chain.doFilter(request, response);
    }
}
