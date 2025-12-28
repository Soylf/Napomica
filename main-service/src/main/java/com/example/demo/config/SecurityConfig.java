package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception { //csrf(AbstractHttpConfigurer::disable);
        http
                .authorizeHttpRequests(auth -> auth
                                .requestMatchers( "/css/**").permitAll()
                                .anyRequest().hasAnyRole("USER", "ADMIN"))
                .formLogin(form -> form
                        .loginPage("/login").loginProcessingUrl("/perform_login")
                        .defaultSuccessUrl("/index.html", true)
                        .permitAll()
                );


        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService () {
        UserDetails admin = User.withUsername("Soyl")
                .password(passwordEncoder().encode("28762876"))
                .roles("ADMIN")
                .build();

        return new InMemoryUserDetailsManager(admin);
    }

    @Bean
    public PasswordEncoder passwordEncoder () {
        return new BCryptPasswordEncoder();
    }
}