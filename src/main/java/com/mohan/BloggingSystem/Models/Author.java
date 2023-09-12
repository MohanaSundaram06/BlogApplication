package com.mohan.BloggingSystem.Models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Table
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank(message = "Please enter your name")
    @Size(min = 2, max = 16, message = "userName must contains minimum 2 and maximum of 16 characters")
    private String username;

    @Email(regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}",
            flags = Pattern.Flag.CASE_INSENSITIVE,message = "Please enter a valid Email Id")
    private String email;

    private String role = "Admin";

    @NotBlank(message = "Please enter a valid password")
    @Size(min = 8, message = "The password must contain minimum 8 characters")
    private String password;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
    private Set<Post> postSet;

}

