package com.AuthService.controllers;

import com.AuthService.dto.*;
import com.AuthService.services.AuthService;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
  private final AuthService authService;
  public AuthController(AuthService authService) {
    this.authService = authService;
  }

  @PostMapping("/signup")
  public ResponseEntity<String> register(@RequestBody SignupRequest req) {
    return ResponseEntity.ok(authService.register(req));
  }

  @PostMapping("/signin")
  public ResponseEntity<JwtResponse> authenticate(@RequestBody LoginRequest req) {
    return ResponseEntity.ok(authService.authenticate(req));
  }
}
