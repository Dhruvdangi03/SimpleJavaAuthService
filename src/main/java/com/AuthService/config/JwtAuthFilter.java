package com.AuthService.config;

import com.AuthService.services.UserDetailsServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.authentication.*;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Configuration
public class JwtAuthFilter extends OncePerRequestFilter {
  @Autowired
  private JwtUtils jwtUtils;
  @Autowired
  private UserDetailsServiceImpl userDetailsSvc;

  @Override
  protected void doFilterInternal(HttpServletRequest req,
                                  HttpServletResponse res,
                                  FilterChain chain)
                                  throws ServletException, IOException {
    String jwt = parseJwt(req);
    if (jwt != null && jwtUtils.validateJwtToken(jwt)) {
      String username = jwtUtils.getUsernameFromJwt(jwt);
      UserDetails userDetails = userDetailsSvc.loadUserByUsername(username);
      UsernamePasswordAuthenticationToken auth =
        new UsernamePasswordAuthenticationToken(
          userDetails, null, userDetails.getAuthorities());
      auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(req));
      SecurityContextHolder.getContext().setAuthentication(auth);
    }
    chain.doFilter(req, res);
  }

  private String parseJwt(HttpServletRequest req) {
    String header = req.getHeader("Authorization");
    if (StringUtils.hasText(header) && header.startsWith("Bearer ")) {
      return header.substring(7);
    }
    return null;
  }
}
