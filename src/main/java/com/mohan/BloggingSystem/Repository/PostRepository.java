package com.mohan.BloggingSystem.Repository;

import com.mohan.BloggingSystem.Models.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post,Integer> {

    List<Post> findAllByAuthorId(Integer authorId);

    List<Post> findAllByTitleContains(String content);

    List<Post> findAllBySubjectContains(String content);

    List<Post> findAllByTagsId(Integer tagId);
}
