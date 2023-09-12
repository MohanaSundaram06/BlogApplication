package com.mohan.BloggingSystem.Contollers;

import com.mohan.BloggingSystem.Models.Tags;
import com.mohan.BloggingSystem.Services.TagsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tags")
public class TagsController {

    @Autowired
    private TagsService tagsService;

    @PostMapping("/author/add/{name}")
    public ResponseEntity<Tags> addTag(@PathVariable("name") String name){
        Tags tag = tagsService.addTag(name);
        return new ResponseEntity<>(tag, HttpStatus.CREATED);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Tags> getTag(@PathVariable("id") int id){
        Tags tag = tagsService.getTag(id);
        return new ResponseEntity<>(tag, HttpStatus.CREATED);
    }
    @GetMapping("/getAll")
    public ResponseEntity<List<Tags>> getTags(){
        List<Tags> tags = tagsService.getTags();
        return new ResponseEntity<>(tags, HttpStatus.CREATED);
    }

    @DeleteMapping("/author/delete/{id}")
    public ResponseEntity<String> deleteTag(@PathVariable("id") int id){
        tagsService.deleteTag(id);
        return new ResponseEntity<>("Tag successfully deleted", HttpStatus.OK);
    }

    @PutMapping("/author/edit/{id}/{name}")
    public ResponseEntity<Tags> editTag(@PathVariable("id") int id,@PathVariable("name") String name){
        Tags tag = tagsService.editTag(id,name);
        return new ResponseEntity<>(tag, HttpStatus.OK);
    }
}










