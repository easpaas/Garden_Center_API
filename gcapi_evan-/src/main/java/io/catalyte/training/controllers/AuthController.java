package io.catalyte.training.controllers;

import io.catalyte.training.exceptions.*;
import io.catalyte.training.Auth.JwtTokenProvider;
import io.catalyte.training.domains.Auth;
import io.catalyte.training.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/auth")
public class AuthController {

  @Autowired
  BCryptPasswordEncoder bCryptPasswordEncoder;

  @Autowired
  AuthenticationManager authenticationManager;

  @Autowired
  JwtTokenProvider jwtTokenProvider;

  @Autowired
  UserRepo users;

  @PostMapping("/signin")
  public ResponseEntity signin(@RequestBody Auth auth) {
    try {
      String username = auth.getUsername();

      authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, auth.getPassword()));
      String token = jwtTokenProvider.createToken(username, this.users.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("Username " + username + "not found")).getRoles());

      Map<Object, Object> model = new HashMap<>();
      model.put("username", username);
      model.put("token", token);
      return ok(model);
    } catch (AuthenticationException e) {
      throw new Unauthorized();
    }
  }
}