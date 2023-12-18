package com.paddown.paddown.security;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.Verification;
import com.paddown.paddown.ConfigProperties;

@Component
public class JWTUtil {
    @Autowired
    private ConfigProperties configProps;
    private final String ISSUER =  "paddown";

    public String generateToken(Map<String,  ?> payload, long  expires){
        LocalDateTime exp =  LocalDateTime.now().plusHours(expires);
        String token =  JWT.create()
                        .withIssuer(this.ISSUER)
                        .withPayload(payload)
                        .withExpiresAt(Date.from(exp.toInstant(ZoneOffset.UTC)))
                        .sign(getAlgorithm());

        return token;
    }
    public String generateToken(Map<String,  ?> payload){
        LocalDateTime exp =  LocalDateTime.now().plusHours(24);
        String token =  JWT.create()
                        .withIssuer(this.ISSUER)
                        .withPayload(payload)
                        .withExpiresAt(Date.from(exp.toInstant(ZoneOffset.UTC)))
                        .sign(getAlgorithm());

        return token;
    }

    public Map<String,String> generateAuthTokens(UserDetails userDetail) throws JWTCreationException{
        Map<String,  String> response = new HashMap<String, String>();
        response.put("auth_token", generateAuthToken(userDetail));
        response.put("refresh_token", generateAuthRefresh(userDetail));

        return  response;
    }

    private String generateAuthToken(UserDetails user) throws JWTCreationException{
        String subject =  user.getUsername();
        LocalDateTime exp =  LocalDateTime.now().plusHours(configProps.tokenexp());
        String token =  JWT.create()
                        .withIssuer(this.ISSUER)
                        .withSubject(subject)
                        .withExpiresAt(Date.from(exp.toInstant(ZoneOffset.UTC)))
                        .sign(getAlgorithm());

        return token;
    }

    private String generateAuthRefresh(UserDetails user) throws JWTCreationException{
        String subject =  user.getUsername();
        LocalDateTime exp =  LocalDateTime.now().plusHours(configProps.refreshexp());
        return JWT.create()
                        .withIssuer(this.ISSUER)
                        .withSubject(subject)
                        .withClaim("token_type", "refresh")
                        .withExpiresAt(Date.from(exp.toInstant(ZoneOffset.UTC)))
                        .sign(getAlgorithm());
    }


    public DecodedJWT decodeToken(String token, Map<String,  String> map) throws JWTVerificationException{
        Verification  verifier =  JWT.require(getAlgorithm());
        for(String key: map.keySet()){
            verifier.withClaim(key, map.get(key));
        }
        
        return verifier.withIssuer(ISSUER).build().verify(token);
    }
    public DecodedJWT decodeRefreshToken(String token) throws JWTVerificationException{
        Map<String, String> claims = Map.of("token_type", "refresh");
        
        return this.decodeToken(token, claims);
    }

    public DecodedJWT decodeToken(String token) throws JWTVerificationException{
        JWTVerifier  verifier =  JWT.require(getAlgorithm())
                                .withIssuer(ISSUER)
                                .build();
        return verifier.verify(token) ;
    }

    private Algorithm getAlgorithm(){
        return Algorithm.HMAC256(configProps.secretKey().getBytes());
    }
}
