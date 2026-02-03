package com.capstone2.book_store2.Service;

import com.capstone2.book_store2.Repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;


/**
 * The type User service test.
 */
class UserServiceTest {
    private UserService userService;
    private UserRepository userRepo;
    private PasswordEncoder  passwordEncoder;
    private AuthenticationManager authenticationManager;

    /**
     * Setup.
     */
    @BeforeEach
    void setup(){
        userRepo = Mockito.mock(UserRepository.class);
        passwordEncoder = Mockito.mock(PasswordEncoder.class);
        authenticationManager = Mockito.mock(AuthenticationManager.class);

        userService = new UserService(userRepo, passwordEncoder, authenticationManager );
    }

    /**
     * Register.
     */
    @Test
    void register() {
        // Create a dummy user
        var user = new com.capstone2.book_store2.Model.UserModel();
        user.setUsername("john");
        user.setPassword("password123");

        // password encoding
        Mockito.when(passwordEncoder.encode("password123")).thenReturn("encodedPassword");

        // save user mock
        Mockito.when(userRepo.save(user)).thenReturn(user);

        // register the user
        userService.register(user);

        // verify password
        Mockito.verify(passwordEncoder).encode("password123");

        // verify repo
        Mockito.verify(userRepo).save(user);
    }


    /**
     * Login.
     */
    @Test
    void login() {
    }

    /**
     * Find by username.
     */
    @Test
    void findByUsername() {
    }
}