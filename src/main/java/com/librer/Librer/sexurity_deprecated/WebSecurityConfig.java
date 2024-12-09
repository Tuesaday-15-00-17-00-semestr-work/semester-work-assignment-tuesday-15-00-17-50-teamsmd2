package com.librer.Librer.sexurity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    private CustomUserDetailsService userDetSer;

    @Autowired
    public WebSecurityConfig(CustomUserDetailsService userDetSer) {
        this.userDetSer = userDetSer;
    }

    @Bean
    @Order(1)
    public SecurityFilterChain RegisterLogin(HttpSecurity http) throws Exception{
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll()
                )
                .securityMatcher("/auth/**")
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }

    @Bean
    @Order(2)
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        // Admin access
                        .requestMatchers("/api/users/**").hasRole("1") // Admin only
                        // Shared access for Admin and User
                        .requestMatchers("/api/books/**", "/api/transactions/**").hasAnyRole("1", "0")
                        // Any other requests must be authenticated
                        .anyRequest().authenticated()
                )
                .httpBasic(Customizer.withDefaults());
        return http.build();
    }

//    @Bean
//    @Order(3)
//    public SecurityFilterChain UserStuff(HttpSecurity http) throws Exception{
//        http
//                .csrf(AbstractHttpConfigurer::disable)
//                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers("/**").hasRole("0")
//                        .anyRequest().authenticated()
//                )
//                .securityMatcher("/**")
//                .httpBasic(Customizer.withDefaults());
//        return http.build();
//
//    }

    @Bean
    public AuthenticationManager authMang(AuthenticationConfiguration authconf) throws Exception{
        return authconf.getAuthenticationManager();
    }

    @Bean
    PasswordEncoder passEnc() {
        return new BCryptPasswordEncoder();
    }

}