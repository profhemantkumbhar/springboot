package com.springsecurity.demo.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configurers.AbstractAuthenticationFilterConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class CustomSecurityConfig {
    @Bean
    public UserDetailsService userDetailsService() {
        // Create user with ROLE_USER
        UserDetails user1 = User.builder()
                .username("user")
//                .password("user")  // {noop} means no password encoding (not recommended for production)
                .password(passwordEncoder().encode("user"))
                .roles("USER")
                .build();

        // Create admin with ROLE_ADMIN
        UserDetails admin = User.builder()
                .username("admin")
//                .password("admin") // {noop} means no password encoding (not recommended for production)
                .password(passwordEncoder().encode("admin"))
                .roles("ADMIN")
                .build();

        // Return In-Memory UserDetailsManager
        return new InMemoryUserDetailsManager(user1, admin);
    }

//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return NoOpPasswordEncoder.getInstance();
//    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    //Authorization
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth -> auth
                .requestMatchers("/admin").hasRole("ADMIN")
                .requestMatchers("/user").hasRole("USER")
                .requestMatchers("/hello").permitAll()
                )
                .formLogin(AbstractAuthenticationFilterConfigurer::permitAll           // Allow everyone to access the login page
                )
                .csrf(csrf -> csrf.disable());
        return http.build();
    }
}
