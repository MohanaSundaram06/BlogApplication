package com.mohan.BloggingSystem.Configuration;

import com.mohan.BloggingSystem.Models.Author;
import com.mohan.BloggingSystem.Services.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private AuthorService authorService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Author author = authorService.getAuthorByEmail(username);
        if (author == null) throw new UsernameNotFoundException("User Not Found");
        return new MyUserDetails(author);
    }
}
