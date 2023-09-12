package com.mohan.BloggingSystem.DTOs.Response;

import com.mohan.BloggingSystem.Models.Tags;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostResponseDto {

    private int id;
    private String title;
    private String subject;
    private int authorId;
    private String authorName;
    private Set<Tags> tags = new HashSet<>();
}
