package com.example.version1.common.authentication;

import jakarta.validation.constraints.Email;

public record LoginRequest(@Email String email, String password) {}
