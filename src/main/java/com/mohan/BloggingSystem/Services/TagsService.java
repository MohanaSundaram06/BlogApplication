package com.mohan.BloggingSystem.Services;

import com.mohan.BloggingSystem.Exceptions.TagAlreadyExistsException;
import com.mohan.BloggingSystem.Exceptions.TagNotFoundExceptions;
import com.mohan.BloggingSystem.Models.Post;
import com.mohan.BloggingSystem.Models.Tags;
import com.mohan.BloggingSystem.Repository.TagsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class TagsService {

    @Autowired
    private TagsRepository tagsRepository;

    public Tags addTag(String name) {
        Tags tag = new Tags();
        if(tagsRepository.existsByName(name)) throw new TagAlreadyExistsException(name);
        tag.setName(name);
        return tagsRepository.save(tag);
    }

    public Tags getTag(int tagId) {
       Optional<Tags> tagsOptional =  tagsRepository.findById(tagId);
       if (tagsOptional.isEmpty()) throw new TagNotFoundExceptions("with Id "+ tagId);
       return tagsOptional.get();
    }

    public void deleteTag(int id) {
        if(!tagsRepository.existsById(id)) throw new TagNotFoundExceptions("with Id "+ id);
        Tags tags = tagsRepository.findById(id).get();

        for(Post post : tags.getPosts()){
            tags.removePost(post);
        }
        tagsRepository.delete(tags);
    }

    public Tags editTag(int tagId, String name) {
        Optional<Tags> tagsOptional =  tagsRepository.findById(tagId);
        if (tagsOptional.isEmpty()) throw new TagNotFoundExceptions("with Id "+ tagId);
        Tags tag = tagsOptional.get();
        if(tagsRepository.existsByName(name)) throw new TagAlreadyExistsException(name);
        tag.setName(name);
        return tagsRepository.save(tag);
    }

    public List<Tags> getTags() {
        List<Tags> tags =  tagsRepository.findAll();
        if (tags.isEmpty()) throw new TagNotFoundExceptions("");
        return tags;
    }
}
