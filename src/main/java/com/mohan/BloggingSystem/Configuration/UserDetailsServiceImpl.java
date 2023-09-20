package com.mohan.BloggingSystem.Configuration;

import com.mohan.BloggingSystem.Models.Author;
import com.mohan.BloggingSystem.Services.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private AuthorService authorService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Author author = authorService.getAuthorByEmail(username);
//        System.out.println("the security "+ SecurityContextHolder.getContext().getAuthentication() == null);
        if (author == null) throw new UsernameNotFoundException("User Not Found");
        return new MyUserDetails(author);
    }
}
