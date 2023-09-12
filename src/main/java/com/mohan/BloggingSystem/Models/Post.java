package com.mohan.BloggingSystem.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table
@Getter @Setter
@Builder
@NoArgsConstructor @AllArgsConstructor
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank()
    @Size(min = 2 ,  message = "The Title Must contain minimum 2 characters")
    private String title;

    @NotBlank()
    @Size(min = 6, message = "The Subject Must contain minimum 12 characters")
    private String subject;

    @ManyToOne
    @JoinColumn
    private Author author;

    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinTable(name = "post_tags",
    joinColumns = {@JoinColumn(name = "post_id")},
    inverseJoinColumns = {@JoinColumn(name = "tag_id")})
    private Set<Tags> tags = new HashSet<>();


}
