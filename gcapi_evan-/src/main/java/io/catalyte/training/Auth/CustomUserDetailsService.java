package io.catalyte.training.Auth;

import io.catalyte.training.domains.Auth;
import io.catalyte.training.domains.User;
import io.catalyte.training.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class CustomUserDetailsService implements UserDetailsService {

  @Autowired
  private UserRepo repo;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Auth auth = new Auth();

    User user = repo.findByEmail(username)
            .orElseThrow(() -> new UsernameNotFoundException("Username: " + username + " not found"));

    auth.setUsername(user.getEmail());
    auth.setPassword(user.getPassword());
    auth.setRoles(user.getRoles());

    return auth;
  }
}