package io.catalyte.training.Auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
@EnableConfigurationProperties
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  @Autowired
  CustomUserDetailsService userDetailsService;

  @Autowired
  JwtTokenProvider jwtTokenProvider;

  @Bean
  @Override
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
            .httpBasic().disable()
            .csrf().disable()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .authorizeRequests()
            .antMatchers("/auth/signin").permitAll()
            .antMatchers(HttpMethod.GET, "/**").permitAll()
            .antMatchers(HttpMethod.POST, "/users").permitAll()
            .antMatchers(HttpMethod.POST, "/**").hasAuthority("Admin")
            .antMatchers(HttpMethod.PUT, "/**").hasAuthority("Admin")
            .antMatchers(HttpMethod.DELETE, "/**").hasAuthority("Admin")
            .anyRequest().permitAll()
            .and()
            .apply(new JwtConfigurer(jwtTokenProvider));
  }
}