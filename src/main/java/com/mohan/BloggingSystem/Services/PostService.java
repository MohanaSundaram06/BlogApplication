package com.mohan.BloggingSystem.Services;

import com.mohan.BloggingSystem.DTOs.Request.PostRequestDto;
import com.mohan.BloggingSystem.DTOs.Response.PostResponseDto;
import com.mohan.BloggingSystem.Exceptions.PostNotFoundException;
import com.mohan.BloggingSystem.Models.Author;
import com.mohan.BloggingSystem.Models.Post;
import com.mohan.BloggingSystem.Models.Tags;
import com.mohan.BloggingSystem.Repository.PostRepository;
import com.mohan.BloggingSystem.Transformers.PostTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;
    @Autowired
    private AuthorService authorService;
    @Autowired
    private TagsService tagsService;

    public PostResponseDto addPost(PostRequestDto postRequestDto) {
        Author author = authorService.getAuthor(postRequestDto.getAuthorId());

        String user = SecurityContextHolder.getContext().getAuthentication().getName();
        if(!user.equals(author.getEmail())) throw new AccessDeniedException("");

        Post post = PostTransformer.RequestToModel(postRequestDto);
        post.setAuthor(author);
        for(int t : postRequestDto.getTagIds()) {
            Tags tag = tagsService.getTag(t);
            if(post.getTags().contains(tag)) continue;
            post.getTags().add(tag);
            tag.getPosts().add(post);
        }
        post = postRepository.save(post);
        return PostTransformer.ModelToResponse(post);
    }

    public Post getPost(Integer postId) {
        Optional<Post> optionalPost = postRepository.findById(postId);
        if (optionalPost.isEmpty()) throw new PostNotFoundException("with Id "+postId);
        return optionalPost.get();
    }

    public List<PostResponseDto> getAllPosts() {
        List<Post> posts = postRepository.findAll();
        List<PostResponseDto> postResponseDtos = new ArrayList<>();
        if(posts.isEmpty()) throw new PostNotFoundException("");
        for(Post post : posts) postResponseDtos.add(PostTransformer.ModelToResponse(post));
        return postResponseDtos;
    }


    public List<PostResponseDto> getAllPostsByAuthor(Integer authorId) {
        List<Post> posts = postRepository.findAllByAuthorId(authorId);

        if(posts.isEmpty()) throw new PostNotFoundException("with Author Id "+authorId);
        List<PostResponseDto> postResponseDtos = new ArrayList<>();
        for(Post post : posts) postResponseDtos.add(PostTransformer.ModelToResponse(post));
        return postResponseDtos;
    }

    public List<PostResponseDto> getAllPostsByTags(Integer tagId) {
        Tags tag = tagsService.getTag(tagId);
        List<Post> posts = postRepository.findAllByTagsId(tagId);

        if(posts.isEmpty()) throw new PostNotFoundException("with Tag Id "+tagId);
        List<PostResponseDto> postResponseDtos = new ArrayList<>();
        for(Post post : posts) postResponseDtos.add(PostTransformer.ModelToResponse(post));
        return postResponseDtos;
    }

    public List<PostResponseDto> getAllPostsByContent(String content) {

        List<Post> posts = postRepository.findAllBySubjectContains(content);
        if(posts.isEmpty()) throw new PostNotFoundException("with content "+content);
        List<PostResponseDto> postResponseDtos = new ArrayList<>();
        for(Post post : posts) postResponseDtos.add(PostTransformer.ModelToResponse(post));
        return postResponseDtos;
    }

    public List<PostResponseDto> getAllPostsByTitle(String name) {
        List<Post> posts = postRepository.findAllByTitleContains(name);
        if(posts.isEmpty()) throw new PostNotFoundException("with Title "+name);
        List<PostResponseDto> postResponseDtos = new ArrayList<>();
        for(Post post : posts) postResponseDtos.add(PostTransformer.ModelToResponse(post));
        return postResponseDtos;
    }

    public void deletePost(Integer id) {
        if(!postRepository.existsById(id)) throw new PostNotFoundException("with Id "+ id);

        Post post = postRepository.findById(id).get();
        Author author = post.getAuthor();
        String user = SecurityContextHolder.getContext().getAuthentication().getName();
        if(!user.equals(author.getEmail())) throw new AccessDeniedException("");

        postRepository.deleteById(id);
    }


    public PostResponseDto editPost(Integer postId, PostRequestDto postRequestDto) {
        if(!postRepository.existsById(postId)) throw new PostNotFoundException("with Id "+ postId);

        Author author = authorService.getAuthor(postRequestDto.getAuthorId());

        String user = SecurityContextHolder.getContext().getAuthentication().getName();
        Author postAuthor = postRepository.findById(postId).get().getAuthor();
        if(!user.equals(author.getEmail()) || author.getId() != postAuthor.getId())
            throw new AccessDeniedException("");

        Post post = PostTransformer.RequestToModel(postRequestDto);
        post.setAuthor(author);
        Set<Tags> tagsSet = postRepository.findById(postId).get().getTags();
        post.setTags(tagsSet);

        for(int t : postRequestDto.getTagIds()) {
            Tags tag = tagsService.getTag(t);
            if(post.getTags().contains(tag)) continue;
            post.getTags().add(tag);
            tag.getPosts().add(post);
        }

        post.setId(postId);
        post = postRepository.save(post);
        return PostTransformer.ModelToResponse(post);
    }
}
