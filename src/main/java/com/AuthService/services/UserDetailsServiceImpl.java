package com.AuthService.services;

import com.AuthService.entities.User;
import com.AuthService.repository.UserRepository;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
  private final UserRepository userRepo;
  public UserDetailsServiceImpl(UserRepository userRepo) {
    this.userRepo = userRepo;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = userRepo.findByUsername(username)
      .orElseThrow();

    return new org.springframework.security.core.userdetails.User(
      user.getUsername(),
      user.getPassword(),
      user.getRoles().stream()
        .map(role -> new SimpleGrantedAuthority(role.name()))
        .collect(Collectors.toList())
    );
  }
}
