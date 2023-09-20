package com.mohan.BloggingSystem.Configuration;


import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;


import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.util.HashMap;


@Component("delegatedAuthenticationEntryPoint")
public class DelegatedAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
//        response.addHeader("access_denied_reason", "AuthenticationEntryPoint");
//        response.sendError(403, "Access Denied");
//        response.getWriter().write("Authentication Required");
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        OutputStream responseStream = response.getOutputStream();
        ObjectMapper mapper = new ObjectMapper();

        System.out.println(authException.getMessage() + " ");
//                + SecurityContextHolder.getContext().getAuthentication().getAuthorities());

        HashMap<String,String> error = new HashMap<>();
        error.put("status", String.valueOf(HttpStatus.UNAUTHORIZED));
        error.put("message","Authentication failed");
        error.put("timestamp", String.valueOf(LocalDateTime.now()));

        mapper.writeValue(responseStream,error);
        responseStream.flush();


    }

}
