package com.mohan.BloggingSystem.Transformers;

import com.mohan.BloggingSystem.DTOs.Request.PostRequestDto;
import com.mohan.BloggingSystem.DTOs.Response.PostResponseDto;
import com.mohan.BloggingSystem.Models.Post;

import java.util.HashSet;

public class PostTransformer {
    public static Post RequestToModel(PostRequestDto postRequestDto) {

        Post post = new Post();
        post.setTitle(postRequestDto.getTitle());
        post.setSubject(postRequestDto.getSubject());

        return post;
    }

    public static PostResponseDto ModelToResponse(Post post) {

        return PostResponseDto.builder()
                .id(post.getId())
                .title(post.getTitle())
                .subject(post.getSubject())
                .tags(post.getTags())
                .authorName(post.getAuthor().getUsername())
                .authorId(post.getAuthor().getId())
                .build();
    }
}
