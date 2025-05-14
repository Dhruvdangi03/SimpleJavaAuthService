package com.AuthService.dto;

import java.util.List;

public class JwtResponse {
  private String token;
  private String type = "Bearer";
  private Long id;
  private String username;
  private List<String> roles;
  // constructor + getters

  public JwtResponse(String token, String type, Long id, String username, List<String> roles) {
    this.token = token;
    this.type = type;
    this.id = id;
    this.username = username;
    this.roles = roles;
  }

  public JwtResponse() {
  }

  public String getToken() {
    return token;
  }

  public String getType() {
    return type;
  }

  public Long getId() {
    return id;
  }

  public String getUsername() {
    return username;
  }

  public List<String> getRoles() {
    return roles;
  }
}
