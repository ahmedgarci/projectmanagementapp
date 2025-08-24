package com.example.demo.Infrastructure.Security;

import java.security.Key;
import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.example.demo.Infrastructure.FileUtils.FileUtils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

    @Value("${spring.security.jwt.secretKey}")
    private String secretKey;
    
    // @Value("${spring.security.privateKey}")
    // private String privateKeyPath;  
    // @Value("${spring.security.publicKey}")
    // private String publicKeyPath;

    public String generateToken(UserDetails userDetails , Map<String,Object> extraClaims){
        return Jwts.builder()
                    .setClaims(extraClaims)
                    .setSubject(userDetails.getUsername())
                    .setExpiration(new Date(System.currentTimeMillis()+ 1000*60*60))
                    .signWith(this.generateSecretKey(secretKey))
                    .compact();
    }

    private Claims extractAllClaimsFromToken(String token){
        return Jwts.parserBuilder()
            .setSigningKey(this.generateSecretKey(secretKey))
            .build()
            .parseClaimsJws(token).getBody();

    }

    private boolean extractJwtExpirationDate(String token){
        Claims allClaims = extractAllClaimsFromToken(token);
        return allClaims.getExpiration().before(new Date());
    }

    public String extractUsernameFromToken(String token) {
        Claims allClaims = extractAllClaimsFromToken(token);
        return allClaims.getSubject();
    }

    public boolean isTokenValid(String token,UserDetails userDetails) {
        return (extractUsernameFromToken(token).equals(userDetails.getUsername()) && !extractJwtExpirationDate(token));
    }
    

    private Key generateSecretKey(String secretString){
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretString));
    }


}
