package com.capstone2.book_store2.Controller;

import com.capstone2.book_store2.Model.UserModel;
import com.capstone2.book_store2.Service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

/**
 * REST Controller for authentication endpoints
 * - Handles HTTP requests
 * - Delegates logic to Service
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class UserController {

    private final UserService userService;




      //Register endpoint

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UserModel user) {
        try {
            userService.register(user);
            return ResponseEntity.ok("User registered successfully!");
        } catch (RuntimeException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }


    // Login endpoint

    @PostMapping("/login")
    public ResponseEntity<String> login(
            @RequestBody UserModel user,
            HttpServletRequest request
    ) {
        try {
            // Authenticate via service
            Authentication auth =
                    userService.login(user.getUsername(), user.getPassword());

            // Store authentication in SecurityContext
            SecurityContextHolder.getContext().setAuthentication(auth);

            // Force session creation (JSESSIONID cookie)
            request.getSession(true);

            return ResponseEntity.ok("Login successful!");
        } catch (Exception ex) {
            return ResponseEntity.status(401)
                    .body("Invalid username or password!");
        }
    }
    // Profile details endpoint
    @GetMapping("/me")
    public UserModel getCurrentUser(Authentication authentication) {
        // Return the current user's details (excluding password)
        String username = authentication.getName();
        UserModel user = userService.findByUsername(username);
        user.setPassword(null); // Hide password
        return user;
    }
}
