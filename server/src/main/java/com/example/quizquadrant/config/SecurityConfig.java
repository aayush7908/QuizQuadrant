package com.example.quizquadrant.config;

import com.example.quizquadrant.model.type.Role;
import com.example.quizquadrant.utils.jwt.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity(debug = true)
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(
                                "/api/auth/**",
                                "/api/user/reset-password"
                        ).permitAll()
                        .requestMatchers(
                                "/api/user/**"
                        ).hasAnyAuthority(Role.STUDENT.name(), Role.TEACHER.name(), Role.ADMIN.name())
                        .requestMatchers(
                                "/api/question/create",
                                "/api/question/update",
                                "/api/question/delete/**",
                                "/api/exam/create",
                                "/api/exam/update",
                                "/api/exam/delete/**"
                        ).hasAnyAuthority(Role.TEACHER.name(), Role.ADMIN.name())
                        .requestMatchers(
                                "/api/subject/create",
                                "/api/subject/update",
                                "/api/subject/delete/**",
                                "/api/subtopic/create",
                                "/api/subtopic/update",
                                "/api/subtopic/delete/**",
                                "/api/admin/**"
                        ).hasAuthority(Role.ADMIN.name())
                        .anyRequest().authenticated()
                )
                .sessionManagement((session) -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

}
