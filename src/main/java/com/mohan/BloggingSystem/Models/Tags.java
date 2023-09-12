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
@AllArgsConstructor @NoArgsConstructor
@Builder
public class Tags {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank
    @Size(min = 3, max = 12, message = "The must contain minimum 3 and maximum 12 characters")
    private String name;

    @ManyToMany(mappedBy = "tags",cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JsonIgnore
    private Set<Post> posts = new HashSet<>();

    public void addPost(Post post){
        this.posts.add(post);
        post.getTags().add(this);
    }

    public void removePost(Post post){
        post.getTags().remove(this);
    }
}
