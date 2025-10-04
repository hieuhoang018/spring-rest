package com.hieuhoang.springrest.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;


public class AuthRequests {
    public static class RegisterRequest {
        @NotBlank public String name;
        @NotBlank @Email public String email;
        @NotBlank public String password;
    }
    public static class LoginRequest {
        @NotBlank @Email public String email;
        @NotBlank public String password;
    }
}
