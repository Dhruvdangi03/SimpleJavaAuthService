package com.AuthService.services;

import com.AuthService.config.JwtUtils;
import com.AuthService.dto.*;
import com.AuthService.entities.Role;
import com.AuthService.entities.User;
import com.AuthService.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class AuthService {
  @Autowired
  private AuthenticationManager authManager;
  @Autowired
  private JwtUtils jwtUtils;
  @Autowired
  private UserRepository userRepo;
  @Autowired
  private PasswordEncoder encoder;

  public JwtResponse authenticate(LoginRequest req) {
    Authentication auth = authManager.authenticate(
      new UsernamePasswordAuthenticationToken(req.getUsername(), req.getPassword()));

    org.springframework.security.core.userdetails.User principal =
      (org.springframework.security.core.userdetails.User) auth.getPrincipal();

    String token = jwtUtils.generateToken(principal);
    List<String> roles = principal.getAuthorities().stream()
      .map(a -> a.getAuthority()).collect(Collectors.toList());

    // you can fetch User entity if you need id
    Long id = userRepo.findByUsername(principal.getUsername()).get().getId();

    return new JwtResponse(token, "Bearer", id, principal.getUsername(), roles);
  }

  public String register(SignupRequest req) {
    if (userRepo.existsByUsername(req.getUsername())) {
      throw new RuntimeException("Username already taken");
    }
    User user = new User();
    user.setUsername(req.getUsername());
    user.setPassword(encoder.encode(req.getPassword()));
    Set<Role> roles = req.getRole().stream()
      .map(r -> Role.valueOf("ROLE_" + r.toUpperCase()))
      .collect(Collectors.toSet());
    user.setRoles(roles);
    userRepo.save(user);
    return "User registered";
  }
}
