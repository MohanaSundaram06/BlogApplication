package com.mohan.BloggingSystem.Services;

import com.mohan.BloggingSystem.Configuration.MyUserDetails;
import com.mohan.BloggingSystem.DTOs.Request.AuthorRequestDto;
import com.mohan.BloggingSystem.DTOs.Request.EditAuthorRequestDto;
import com.mohan.BloggingSystem.DTOs.Response.AuthorResponseDto;
import com.mohan.BloggingSystem.Exceptions.AuthorNotFoundException;
import com.mohan.BloggingSystem.Exceptions.UserAlreadyExistsException;
import com.mohan.BloggingSystem.Models.Author;
import com.mohan.BloggingSystem.Repository.AuthorRepository;
import com.mohan.BloggingSystem.Transformers.AuthorTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AuthorService {

    @Autowired
    private AuthorRepository authorRepository;

    public AuthorResponseDto addAuthor(AuthorRequestDto authorRequestDto) {

        if(authorRepository.existsByEmail(authorRequestDto.getEmail())) throw new UserAlreadyExistsException(authorRequestDto.getEmail());

        Author author = AuthorTransformer.RequestToModel(authorRequestDto);
//        passwordencryption
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        author.setPassword(passwordEncoder.encode(authorRequestDto.getPassword()));

        author = authorRepository.save(author);
        return AuthorTransformer.ModelToResponse(author);
    }

    public Author getAuthor(Integer id) {
        Optional<Author> authorOptional = authorRepository.findById(id);
        if(authorOptional.isEmpty()) throw new AuthorNotFoundException("with id " + id);
        return authorOptional.get();
    }

    public void deleteAuthor(Integer id) {
        if(!authorRepository.existsById(id)) throw new AuthorNotFoundException("with id " + id);

        String user = SecurityContextHolder.getContext().getAuthentication().getName();
        Author author = authorRepository.findById(id).get();

        if(!user.equals(author.getEmail())) throw new AccessDeniedException("");

        authorRepository.deleteById(id);
    }

    public AuthorResponseDto updateAuthor(Integer id, EditAuthorRequestDto authorRequestDto) {
        System.out.println("true");
        Optional<Author> authorOptional = authorRepository.findById(id);
        if(authorOptional.isEmpty()) throw new AuthorNotFoundException("with id " + id);

        String user = SecurityContextHolder.getContext().getAuthentication().getName();

        Author author = authorOptional.get();

        if(!user.equals(author.getEmail())) throw new AccessDeniedException("");

        author.setUsername(authorRequestDto.getUsername());
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        author.setPassword(passwordEncoder.encode(authorRequestDto.getPassword()));

        author = authorRepository.save(author);
        return AuthorTransformer.ModelToResponse(author);
    }

    public List<AuthorResponseDto> getAuthors() {
        List<Author> authorList = authorRepository.findAll();
        if(authorList.isEmpty()) throw new AuthorNotFoundException("");

        List<AuthorResponseDto> authorResponseDtos = new ArrayList<>();
        for(Author author : authorList) authorResponseDtos.add(AuthorTransformer.ModelToResponse(author));

        return authorResponseDtos;
    }
    public Author getAuthorByEmail(String email){
        Optional<Author> author = authorRepository.findByEmail(email);
        if(author.isEmpty()) return null;
        return author.get();
    }
}
