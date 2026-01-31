package com.capstone2.book_store2.Controller;

import com.capstone2.book_store2.Model.userModel;
import com.capstone2.book_store2.Service.userService;
import jakarta.servlet.http.HttpServletRequest;
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
@RequestMapping("/auth")
public class userController {

    private final userService userService;

    // Inject service only
    public userController(userService userService) {
        this.userService = userService;
    }


      //Register endpoint

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody userModel user) {
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
            @RequestBody userModel user,
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
}
