package com.mohan.BloggingSystem.Contollers;

import com.mohan.BloggingSystem.DTOs.Request.AuthRequest;
import com.mohan.BloggingSystem.DTOs.Response.AuthResponse;
import com.mohan.BloggingSystem.Exceptions.ApiErrorResponse;
import com.mohan.BloggingSystem.JWTConfig.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SecurityController {

    @Autowired
    private  AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @PostMapping("/authenticate")
    public ResponseEntity<Object> authenticate(@RequestBody AuthRequest authRequest){

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));

        if (! authentication.isAuthenticated())
            new ResponseEntity<>(new ApiErrorResponse(HttpStatus.BAD_GATEWAY,
                    "Invalid Credentials"),HttpStatus.BAD_REQUEST);

        String token = jwtService.generateToken(authRequest.getUsername());

        return new ResponseEntity<>(new AuthResponse(token), HttpStatus.OK);
    }

//    @PostMapping("/logged-out")
//    public ResponseEntity<String> loggedOut(){
//        System.out.println("logged out");
//        return new ResponseEntity<>("successfully logged out",HttpStatus.OK);
//    }
//
//    @GetMapping("/access-denied")
//    public ResponseEntity<String> accessDenied(){
//        return new ResponseEntity<>("Access Denied",HttpStatus.UNAUTHORIZED);
//    }
//    @GetMapping("/login-error")
//    public ResponseEntity<String> loginError(){
//        return new ResponseEntity<>("Enter Valid Credentials",HttpStatus.BAD_REQUEST);
//    }
}
