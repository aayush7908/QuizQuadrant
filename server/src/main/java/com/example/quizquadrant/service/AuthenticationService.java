package com.example.quizquadrant.service;

import com.example.quizquadrant.config.JwtService;
import com.example.quizquadrant.dto.AuthenticationRequestDto;
import com.example.quizquadrant.dto.AuthenticationResponseDto;
import com.example.quizquadrant.dto.RegisterRequestDto;
import com.example.quizquadrant.model.User;
import com.example.quizquadrant.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponseDto register(RegisterRequestDto request) {
        Optional<User> userOptional = userRepository.findByEmail(request.email());
        if (userOptional.isEmpty()) {
            var user = User.builder()
                    .type(request.type())
                    .name(request.name())
                    .email(request.email())
                    .password(passwordEncoder.encode(request.password()))
                    .build();
            user = userRepository.save(user);
            var jwtToken = jwtService.generateToken(user);
            return new AuthenticationResponseDto(jwtToken, user.getType(), user.getName(), user.getEmail(), user.getId());
        } else {
            return null;
        }
    }

    public AuthenticationResponseDto login(AuthenticationRequestDto request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.email(),
                        request.password()
                )
        );
        var user = userRepository.findByEmail(request.email()).orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        return new AuthenticationResponseDto(jwtToken, user.getType(), user.getName(), user.getEmail(), user.getId());
    }

    public AuthenticationResponseDto authenticate() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails authenticatedUser = (UserDetails) authentication.getPrincipal();
        var user = userRepository.findByEmail(authenticatedUser.getUsername()).orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        return new AuthenticationResponseDto(jwtToken, user.getType(), user.getName(), user.getEmail(), user.getId());
    }
}
