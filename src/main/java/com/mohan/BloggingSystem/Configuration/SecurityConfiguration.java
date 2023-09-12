package com.mohan.BloggingSystem.Configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
//    @Autowired
//    @Qualifier("delegatedAuthenticationEntryPoint")
//    AuthenticationEntryPoint authEntryPoint;

    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsServiceImpl();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.csrf().disable().httpBasic().and()
                .authorizeHttpRequests()
                .requestMatchers("/","/author/add","/author/get/*","/author/getAll",
                        "/tags/get/*","/tags/getAll",
                        "/posts/get/**","/posts/getAll","/logout",
                        "/access-denied","/customError","/loggedIn","/login","/logged-out").permitAll()

                .requestMatchers("/author/edit/**","/author/delete/**","/author/loggedIn",
                        "/posts/author/**","/tags/author/**").hasAuthority("Admin")

                .and()
                .formLogin().loginPage("/login")
                .successForwardUrl("/loggedIn")
//                .defaultSuccessUrl("/loggedIn")
                .permitAll()
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(new DelegatedAuthenticationEntryPoint())
//                .accessDeniedHandler(new CustomAccessDeniedHandler())
                .and()
                .logout()
                .logoutSuccessUrl("/logged-out")
                .permitAll();

        return http.build();

    }
}
