package com.paddown.paddown.security.filters;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;


@AllArgsConstructor
public class JWTFilter extends OncePerRequestFilter {
    
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String authHeader =  request.getHeader("Authorization");
        
        if (authHeader ==  null  || !authHeader.startsWith("Bearer")){
            filterChain.doFilter(request, response);
            return;
        }
        
        String token =  authHeader.replace("Bearer", "");
        // decode jwt
    
        // if jwt is valid and user exists
            // get user details object for user
            // create an authentication object with userdetail
            // add authentication object to security context if context is empty
        if(SecurityContextHolder.getContext().getAuthentication() == null){
                SecurityContext context = SecurityContextHolder.createEmptyContext();
                UserDetails userdetail =  CustomUserDetail();
                Authentication authentication = new UsernamePasswordAuthenticationToken(userdetail,  null) ;
                context.setAuthentication(authentication);
                SecurityContextHolder.setContext(context);
        }
        filterChain.doFilter(request, response);
    }
}
