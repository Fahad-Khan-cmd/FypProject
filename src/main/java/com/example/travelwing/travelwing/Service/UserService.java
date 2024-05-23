package com.example.travelwing.travelwing.Service;

import com.example.travelwing.travelwing.Config.JwtService;
import com.example.travelwing.travelwing.Controller.AuthenticationRequest;
import com.example.travelwing.travelwing.Controller.AuthenticationResponse;
import com.example.travelwing.travelwing.Controller.RegisterRequest;
import com.example.travelwing.travelwing.Domain.Role;
import com.example.travelwing.travelwing.Domain.User;
import com.example.travelwing.travelwing.Repository.UserRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class UserService  {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {
        var user = User.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();
        userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public User findByEmail(String email) {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        return optionalUser.orElseThrow(() -> new RuntimeException("User not found"));
    }

    public void saveUser(User user) {
        userRepository.save(user);
    }

    public User updateUser(String email, User updatedUser) {
        User user = findByEmail(email);
        user.setFirstname(updatedUser.getFirstname());
        user.setLastname(updatedUser.getLastname());
        user.setEmail(updatedUser.getEmail());
        return userRepository.save(user);
    }


}
