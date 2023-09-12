package com.mohan.BloggingSystem.Contollers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SecurityController {

    @PostMapping("/loggedIn")
    public ResponseEntity<String> loggedIn(){
        return new ResponseEntity<>("successfully logged in " , HttpStatus.OK);
    }

    @PostMapping("/logged-out")
    public ResponseEntity<String> loggedOut(){
        System.out.println("logged out");
        return new ResponseEntity<>("successfully logged out",HttpStatus.OK);
    }

    @GetMapping("/access-denied")
    public ResponseEntity<String> accessDenied(){
        return new ResponseEntity<>("Access Denied",HttpStatus.UNAUTHORIZED);
    }
    @GetMapping("/login-error")
    public ResponseEntity<String> loginError(){
        return new ResponseEntity<>("Enter Valid Credentials",HttpStatus.BAD_REQUEST);
    }
}
