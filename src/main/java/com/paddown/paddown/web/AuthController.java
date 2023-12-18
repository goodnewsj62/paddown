package com.paddown.paddown.web;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.paddown.paddown.data.Account;
import com.paddown.paddown.security.CustomUserDetailService;
import com.paddown.paddown.security.JWTUtil;
import com.paddown.paddown.security.models.AuthModel;
import com.paddown.paddown.security.models.RefreshModel;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;



@RestController
@RequestMapping("/api/v1/auth")
@AllArgsConstructor
public class AuthController {

    AuthenticationManager authenticationManager;
    JWTUtil jwtUtil;
    CustomUserDetailService customUserDetailService;
    
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
    //     Authentication authenticationRequest = UsernamePasswordAuthenticationToken.unauthenticated(auth.getEmail(), auth.getPassword());
	// 	Authentication authenticationResponse = this.authenticationManager.authenticate(authenticationRequest);

    //     if(!authenticationResponse.isAuthenticated()){
    //         throw new Exception("Bad credentials");
    //     }
    //     UserDetails userDetail =  (UserDetails)authenticationResponse.getPrincipal();
    //     Map<String,  String> response = new HashMap<String, String>();
    //     response.put("auth_token", jwtUtil.generateAuthToken(userDetail));
    //     response.put("refresh_token", jwtUtil.generateAuthRefresh(userDetail));

    //     return  ResponseEntity.ok().body(response);
        return ResponseEntity.status(HttpStatus.OK).build();
    }


    @GetMapping("/refresh")
    public ResponseEntity<Map<String, String>> refresh(@Valid @RequestBody  RefreshModel  data){
        DecodedJWT  payload= jwtUtil.decodeRefreshToken(data.getRefresh());
        UserDetails userDetail= customUserDetailService.loadUserByUsername(payload.getSubject());
        return  ResponseEntity.ok().body(jwtUtil.generateAuthTokens(userDetail));
    }
}
