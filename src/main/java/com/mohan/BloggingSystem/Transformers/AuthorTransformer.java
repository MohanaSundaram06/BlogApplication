package com.mohan.BloggingSystem.Transformers;

import com.mohan.BloggingSystem.DTOs.Request.AuthorRequestDto;
import com.mohan.BloggingSystem.DTOs.Response.AuthorResponseDto;
import com.mohan.BloggingSystem.Models.Author;

public class AuthorTransformer {

    public static Author RequestToModel(AuthorRequestDto authorRequestDto) {

        Author author = new Author();
        author.setUsername(authorRequestDto.getUsername());
        author.setEmail(authorRequestDto.getEmail());
        return author;

    }

    public static AuthorResponseDto ModelToResponse(Author author) {

        return AuthorResponseDto.builder()
                .id(author.getId())
                .username(author.getUsername())
                .email(author.getEmail())
                .build();
    }

}
