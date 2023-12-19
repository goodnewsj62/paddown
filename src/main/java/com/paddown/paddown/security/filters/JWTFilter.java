package com.paddown.paddown.security.filters;

import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.paddown.paddown.data.Account;
import com.paddown.paddown.respository.AccountRepo;
import com.paddown.paddown.security.CustomUserDetail;
import com.paddown.paddown.security.JWTUtil;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;


@AllArgsConstructor
@Component
public class JWTFilter extends OncePerRequestFilter {
    @Autowired
    AccountRepo accountRepository;
    @Autowired
    JWTUtil jwtUtil;
    
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String authHeader =  request.getHeader("Authorization");
        
        if (authHeader ==  null  || !authHeader.startsWith("Bearer")){
            filterChain.doFilter(request, response);
            return;
        }
        
        String token =  authHeader.replace("Bearer ", "");
        DecodedJWT payload;

        try{
            payload =  jwtUtil.decodeToken(token);
        }catch(JWTVerificationException exception){
            filterChain.doFilter(request, response);
            return;
        }

        String  email =  payload.getSubject();
        Optional<Account> account = accountRepository.findByEmail(email);

        if(!account.isPresent()){
            filterChain.doFilter(request, response);
            return;
        }
        
        

        if(SecurityContextHolder.getContext().getAuthentication() == null){
                SecurityContext context = SecurityContextHolder.createEmptyContext();
                UserDetails userdetail =  new CustomUserDetail(account.get());
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userdetail,  null,userdetail.getAuthorities()) ;
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                context.setAuthentication(authentication);
                SecurityContextHolder.setContext(context);
        }


        filterChain.doFilter(request, response);
    }
}
