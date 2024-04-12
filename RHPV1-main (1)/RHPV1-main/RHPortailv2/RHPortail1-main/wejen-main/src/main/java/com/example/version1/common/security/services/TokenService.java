package com.example.version1.common.security.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jose.jws.JwsAlgorithms;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Service
@RequiredArgsConstructor
public class TokenService {

  private final JwtEncoder jwtEncoder;

  public String generateToken(Authentication authentication) {
    var authorities =
        authentication.getAuthorities();
    var instantOfNow = Instant.now();
    JwtClaimsSet claimsSet =
        JwtClaimsSet.builder()
            .issuer("projet_example")
            .issuedAt(instantOfNow)
            .expiresAt(instantOfNow.plus(1, ChronoUnit.HOURS))
            .subject(authentication.getName())
            .claim("scope", authorities)
            .build();
    return jwtEncoder
        .encode(
            JwtEncoderParameters.from(JwsHeader.with(() -> JwsAlgorithms.RS256).build(), claimsSet))
        .getTokenValue();
  }
}
