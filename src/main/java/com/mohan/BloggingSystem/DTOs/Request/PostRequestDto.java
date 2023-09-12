package com.mohan.BloggingSystem.DTOs.Request;

import lombok.Data;

import java.util.List;

@Data
public class PostRequestDto {

    private String title;
    private String subject;
    private int authorId;
    private List<Integer> tagIds;
}
