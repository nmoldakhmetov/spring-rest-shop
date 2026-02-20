package com.nursultan.controller;

import com.nursultan.dao.UserRepository;
import com.nursultan.dto.AuthResponse;
import com.nursultan.dto.LoginRequest;
import com.nursultan.dto.RegisterRequest;
import com.nursultan.entity.User;
import com.nursultan.security.JwtCore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder; // Наш шифровальщик из SecurityConfig

    @Autowired
    private AuthenticationManager authenticationManager; // Главный проверяющий паролей

    @Autowired
    private JwtCore jwtCore; // Наш печатный станок токенов

    // ==========================================
    // 1. РЕГИСТРАЦИЯ
    // ==========================================
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest registerRequest) {


        if (userRepository.findByUserName(registerRequest.getUserName()).isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Пользователь с таким именем уже существует");
        }


        User user = new User();
        user.setUserName(registerRequest.getUserName());
        user.setUserSurname(registerRequest.getUserSurname());
        user.setBalance(1000);
        user.setRole("ROLE_USER");

        String hashedPwd = passwordEncoder.encode(registerRequest.getPassword());
        user.setPassword(hashedPwd);

        userRepository.save(user);

        return ResponseEntity.ok("Пользователь успешно зарегистрирован!");
    }


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUserName(), loginRequest.getPassword())
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);

            UserDetails userDetails = (UserDetails) authentication.getPrincipal();


            String jwt = jwtCore.generateToken(userDetails);


            return ResponseEntity.ok(new AuthResponse(jwt));

        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Неверный логин или пароль");
        }
    }
}