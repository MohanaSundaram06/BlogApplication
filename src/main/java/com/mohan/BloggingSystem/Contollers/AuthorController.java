package com.mohan.BloggingSystem.Contollers;

import com.mohan.BloggingSystem.DTOs.Request.AuthorRequestDto;
import com.mohan.BloggingSystem.DTOs.Request.EditAuthorRequestDto;
import com.mohan.BloggingSystem.DTOs.Response.AuthorResponseDto;
import com.mohan.BloggingSystem.Services.AuthorService;
import com.mohan.BloggingSystem.Transformers.AuthorTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/author")
public class AuthorController {

    @Autowired
    private AuthorService authorService;

    @PostMapping("/add")
    public ResponseEntity<AuthorResponseDto> addAuthor(@RequestBody AuthorRequestDto authorRequestDto){
        AuthorResponseDto responseDto = authorService.addAuthor(authorRequestDto);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<AuthorResponseDto> getAuthor(@PathVariable("id") Integer id){
        AuthorResponseDto responseDto = AuthorTransformer.ModelToResponse(authorService.getAuthor(id));
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }
    @GetMapping("/getAll")
    public ResponseEntity<List<AuthorResponseDto>> getAuthors(){
        List<AuthorResponseDto> responseDtos = authorService.getAuthors();
        return new ResponseEntity<>(responseDtos, HttpStatus.CREATED);
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<AuthorResponseDto> updateAuthor(@PathVariable("id") Integer id,@RequestBody EditAuthorRequestDto editauthorRequestDto){
        AuthorResponseDto responseDto = authorService.updateAuthor(id, editauthorRequestDto);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteAuthor(@PathVariable("id") Integer id){
        authorService.deleteAuthor(id);
        return new ResponseEntity<>("Author deleted successfully", HttpStatus.OK);
    }

}
