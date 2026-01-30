package com.capstone2.book_store2.Controller;

import com.capstone2.book_store2.Model.userModel;
import com.capstone2.book_store2.Repository.userRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import java.util.Optional;

// login & registration
@RestController
@RequestMapping("/auth")
public class userController {

    private final userRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public userController(userRepository userRepository,
                          PasswordEncoder passwordEncoder,
                          AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    // Register unchanged (but avoid double save)
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody userModel user) {
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            return ResponseEntity.badRequest().body("Username already exists!");
        }
        if (user.getUsername() == null || user.getPassword() == null) {
            return ResponseEntity.badRequest().body("Username and password are required");
        }
        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(user);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Registration failed, try again later");
        }
        return ResponseEntity.ok("User registered successfully!");
    }

    // Login: authenticate and create session
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody userModel user, HttpServletRequest request) {
        try {
            UsernamePasswordAuthenticationToken token =
                    new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
            Authentication auth = authenticationManager.authenticate(token);

            // store authentication in security context -> session will be created and JSESSIONID set
            SecurityContextHolder.getContext().setAuthentication(auth);
            request.getSession(true); // create session so server issues Set-Cookie: JSESSIONID

            return ResponseEntity.ok("Login successful!");
        } catch (AuthenticationException ex) {
            return ResponseEntity.status(401).body("Invalid username or password!");
        }
    }
}