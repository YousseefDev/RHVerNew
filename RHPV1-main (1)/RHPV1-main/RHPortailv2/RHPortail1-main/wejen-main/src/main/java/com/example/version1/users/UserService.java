  package com.example.version1.users;
  
  import lombok.RequiredArgsConstructor;
  import org.springframework.security.crypto.password.PasswordEncoder;
  import org.springframework.stereotype.Service;
  
  @Service
  @RequiredArgsConstructor
  public class UserService {
  
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
  
    public Long register(User user) {
      user.setPassword(passwordEncoder.encode(user.getPassword()));
      return userRepository.save(user).getId();
    }
  }
