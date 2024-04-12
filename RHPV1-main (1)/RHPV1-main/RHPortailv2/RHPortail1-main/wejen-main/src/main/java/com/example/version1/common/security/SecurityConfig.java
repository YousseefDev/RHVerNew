package com.example.version1.common.security;

import com.example.version1.users.Role;
import com.example.version1.users.UserRepository;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.gen.RSAKeyGenerator;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.server.resource.web.BearerTokenAuthenticationEntryPoint;
import org.springframework.security.oauth2.server.resource.web.access.BearerTokenAccessDeniedHandler;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

  private final UserRepository userRepository;
  private static RSAKey rsaKey;

  @Bean
  public SecurityFilterChain apiSecurityFilterChain(HttpSecurity httpSecurity) throws Exception {
    return httpSecurity
            .securityMatcher("/api/**")
            .authorizeHttpRequests(
                    matcherRegistry ->
                            matcherRegistry
                                    .requestMatchers("/api/v1/auth/**")
                                    .permitAll()
                                    // Allow authenticated users with any role to access requests
                                    .requestMatchers(HttpMethod.POST, "/api/v1/requests")
                                    .authenticated()
                                    .requestMatchers("/api/v1/requests/**")

                                    .authenticated())
            .csrf(CsrfConfigurer::disable)
            .sessionManagement((smc) -> smc.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .oauth2ResourceServer((o2rsc) -> o2rsc.jwt(Customizer.withDefaults()))
            .exceptionHandling(
                    ehc ->
                            ehc.accessDeniedHandler(new BearerTokenAccessDeniedHandler())
                                    .authenticationEntryPoint(new BearerTokenAuthenticationEntryPoint()))
            .build();
  }


  @Bean
  public JWKSource<SecurityContext> jwkSource() throws JOSEException {
    rsaKey = new RSAKeyGenerator(2048).generate();
    JWKSet jwkSet = new JWKSet(rsaKey);
    return (jwkSelector, securityContext) -> jwkSelector.select(jwkSet);
  }

  @Bean
  public JwtEncoder jwtEncoder(JWKSource<SecurityContext> jwkSource) {
    return new NimbusJwtEncoder(jwkSource);
  }

  @Bean
  public JwtDecoder jwtDecoder() throws JOSEException {
    return NimbusJwtDecoder.withPublicKey(rsaKey.toRSAPublicKey()).build();
  }

  @Bean
  public AuthenticationManager authenticationManager(PasswordEncoder passwordEncoder) {
    var daoAuthenticationProvider = new DaoAuthenticationProvider(passwordEncoder);
    daoAuthenticationProvider.setUserDetailsService(
        email ->
            userRepository
                .findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found")));
    return new ProviderManager(daoAuthenticationProvider);
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
