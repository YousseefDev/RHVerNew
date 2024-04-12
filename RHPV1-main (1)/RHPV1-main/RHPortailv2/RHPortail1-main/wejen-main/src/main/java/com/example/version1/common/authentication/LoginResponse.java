package com.example.version1.common.authentication;

import lombok.Builder;

@Builder
public record LoginResponse(String access_token) {}
