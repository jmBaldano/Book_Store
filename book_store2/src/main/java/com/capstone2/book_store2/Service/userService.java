package com.capstone2.book_store2.Service;

import com.capstone2.book_store2.Model.userModel;
import com.capstone2.book_store2.Repository.userRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Service layer for User authentication and registration
 * - Contains business logic
 * - Handles password encoding and authentication
 */
@Service
public class userService {

    private final userRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    // Constructor injection (best practice)
    public userService(userRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    /**
     * Register a new user
     *
     * @param user user data from request
     * @throws RuntimeException if username exists or invalid data
     */
    public void register(userModel user) {

        if (user.getUsername() == null || user.getPassword() == null) {
            throw new RuntimeException("Username and password are required");
        }

        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new RuntimeException("Username already exists");
        }

        // Encode password before saving
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    /**
     * Authenticate user credentials
     *
     * @param username username
     * @param password raw password
     * @return authenticated Authentication object
     */
    public Authentication login(String username, String password) {

        UsernamePasswordAuthenticationToken token =
                new UsernamePasswordAuthenticationToken(username, password);

        // Will throw AuthenticationException if invalid
        return authenticationManager.authenticate(token);
    }
}
