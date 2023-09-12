package com.mohan.BloggingSystem.Contollers;

import com.mohan.BloggingSystem.DTOs.Request.PostRequestDto;
import com.mohan.BloggingSystem.DTOs.Response.PostResponseDto;
import com.mohan.BloggingSystem.Services.PostService;
import com.mohan.BloggingSystem.Transformers.PostTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @PostMapping("/author/add")
    public ResponseEntity<PostResponseDto> addPost(@RequestBody PostRequestDto postRequestDto){
        PostResponseDto postResponseDto = postService.addPost(postRequestDto);
        return new ResponseEntity<>(postResponseDto, HttpStatus.CREATED);
    }

    @PutMapping("/author/edit/{id}")
    public ResponseEntity<PostResponseDto> editPost(@PathVariable("id") Integer postId, @RequestBody PostRequestDto postRequestDto){
        PostResponseDto postResponseDto = postService.editPost(postId,postRequestDto);
        return new ResponseEntity<>(postResponseDto, HttpStatus.CREATED);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<PostResponseDto> getPost(@PathVariable("id") Integer postId){
        PostResponseDto postResponseDto = PostTransformer.ModelToResponse(postService.getPost(postId));
        return new ResponseEntity<>(postResponseDto, HttpStatus.OK);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<PostResponseDto>> getAllPosts(){
        List<PostResponseDto> postResponseDto = postService.getAllPosts();
        return new ResponseEntity<>(postResponseDto, HttpStatus.OK);
    }

    @GetMapping("/get/by-author/{id}")
    public ResponseEntity<List<PostResponseDto>> getAllPostsByAuthor(@PathVariable("id") Integer authorId){
        List<PostResponseDto> postResponseDto = postService.getAllPostsByAuthor(authorId);
        return new ResponseEntity<>(postResponseDto, HttpStatus.OK);
    }

    @GetMapping("/get/by-tag/{id}")
    public ResponseEntity<List<PostResponseDto>> getAllPostsByTags(@PathVariable("id") Integer tagId){
        List<PostResponseDto> postResponseDto = postService.getAllPostsByTags(tagId);
        return new ResponseEntity<>(postResponseDto, HttpStatus.OK);
    }

    @GetMapping("/get/by-subject/{content}")
    public ResponseEntity<List<PostResponseDto>> getAllPostsByContent(@PathVariable("content") String content){
        List<PostResponseDto> postResponseDto = postService.getAllPostsByContent(content);
        return new ResponseEntity<>(postResponseDto, HttpStatus.OK);
    }

    @GetMapping("/get/by-title/{name}")
    public ResponseEntity<List<PostResponseDto>> getAllPostsByTitle(@PathVariable("name") String name){
        List<PostResponseDto> postResponseDto = postService.getAllPostsByTitle(name);
        return new ResponseEntity<>(postResponseDto, HttpStatus.OK);
    }

    @DeleteMapping("/author/delete/{id}")
    public ResponseEntity<String> deletePost(@PathVariable("id") Integer id){
        postService.deletePost(id);
        return new ResponseEntity<>("Post deleted successfully", HttpStatus.OK);
    }
}
