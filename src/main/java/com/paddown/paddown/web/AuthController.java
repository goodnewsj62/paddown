package com.paddown.paddown.web;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.paddown.paddown.data.Account;
import com.paddown.paddown.security.CustomUserDetailService;
import com.paddown.paddown.security.JWTUtil;
import com.paddown.paddown.security.models.AuthModel;
import com.paddown.paddown.security.models.RefreshModel;
import com.paddown.paddown.services.AccountService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;



@RestController
@RequestMapping("/api/v1/auth")
@AllArgsConstructor
public class AuthController {

    AuthenticationManager authenticationManager;
    JWTUtil jwtUtil;
    CustomUserDetailService customUserDetailService;
    AccountService accountService;
    
    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> authenticate(@Valid @RequestBody AuthModel auth ) throws BadCredentialsException,  JWTCreationException, Exception{
        Authentication authenticationRequest = UsernamePasswordAuthenticationToken.unauthenticated(auth.getEmail(), auth.getPassword());
		Authentication authenticationResponse = this.authenticationManager.authenticate(authenticationRequest);

        if(!authenticationResponse.isAuthenticated()){
            throw new Exception("Bad credentials");
        }
        UserDetails userDetail =  (UserDetails)authenticationResponse.getPrincipal();

        return  ResponseEntity.ok().body(jwtUtil.generateAuthTokens(userDetail));
    }

    @PostMapping("/signup")
    public ResponseEntity<HttpStatus> register(@Valid @RequestBody Account auth ){
        accountService.createAccount(auth);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


    @PostMapping("/refresh")
    public ResponseEntity<Map<String, String>> refresh(@Valid @RequestBody  RefreshModel  data) throws JWTVerificationException, UsernameNotFoundException{
        DecodedJWT  payload= jwtUtil.decodeRefreshToken(data.getRefresh());
        UserDetails userDetail= customUserDetailService.loadUserByUsername(payload.getSubject());
        return  ResponseEntity.ok().body(jwtUtil.generateAuthTokens(userDetail));
    }
}
