//package com.librer.Librer.sexurity;
//
//import com.librer.Librer.user.*;
//import jakarta.validation.Valid;
//import org.springframework.http.HttpStatus;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.security.authentication.BadCredentialsException;
//import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.web.server.ResponseStatusException;
//
//@RestController
//@RequestMapping("/auth")
//public class SexController {
//
//    private final AuthenticationManager authenticationManager;
//    private final UserRepository userRepository;
//    private final PasswordEncoder passwordEncoder;
//
//    public SexController(AuthenticationManager authenticationManager,
//                         PasswordEncoder passwordEncoder,
//                         UserRepository userRepository) {
//        this.authenticationManager = authenticationManager;
//        this.passwordEncoder = passwordEncoder;
//        this.userRepository = userRepository;
//    }
//
//    // Login
//    @ResponseStatus(HttpStatus.OK)
//    @PostMapping("/login")
//    public String login(@Valid @RequestBody UserRecord user) {
//        if (user.email() == null || user.email().isBlank()) {
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email must not be blank");
//        }
//        try {
//            Authentication authentication = authenticationManager.authenticate(
//                    new UsernamePasswordAuthenticationToken(user.email(), user.password())
//            );
//            SecurityContextHolder.getContext().setAuthentication(authentication);
//            return "Login successful"; // Return a success message
//        } catch (BadCredentialsException e) {
//            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid username or password");
//        }
//    }
//
//    // Register
//    @ResponseStatus(HttpStatus.CREATED)
//    @PostMapping("/register")
//    public void create(@Valid @RequestBody UserRecord user) {
//        String encodedPassword = passwordEncoder.encode(user.password());
//        UserRecord newUser = new UserRecord(0,user.username(), encodedPassword, user.role_id(), user.email());
//        userRepository.create(newUser);
//    }
//
//    // Logout
//    @ResponseStatus(HttpStatus.NO_CONTENT)
//    @PostMapping("/logout")
//    public void logout() {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if (authentication != null) {
//            new SecurityContextLogoutHandler().logout(null, null, authentication); // Log out the user
//        }
//    }
//}
