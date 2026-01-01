package com.example.demo.Infrastructure.Security;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.example.demo.Infrastructure.FileUtils.FileUtils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class JwtService {

    @Value("${spring.security.jwt.secretKey}")
    private String secretKey;
    @Value("${spring.security.privateKey}")
    private String privateKeyPath;
    @Value("${spring.security.publicKey}")
    private String publicKeyPath;

    private PrivateKey privateKey;
    private PublicKey publicKey;

    @PostConstruct
    public void initKeys() {
        this.privateKey = FileUtils.loadPrivateKey(privateKeyPath);
        this.publicKey = FileUtils.loadPublicKey(publicKeyPath);

        if (privateKey == null || publicKey == null) {
            throw new RuntimeException("Failed to load RSA keys");
        }
    }

    public String generateToken(UserDetails userDetails , Map<String,Object> extraClaims){
        return Jwts.builder()
                    .setClaims(extraClaims)
                    .setSubject(userDetails.getUsername())
                    .setExpiration(new Date(System.currentTimeMillis()+ 1000*60*30))//     // 30mins
//                    .signWith(this.generateSecretKey(secretKey))
                    .signWith(privateKey)
                    .compact();
    }

    private Claims extractAllClaimsFromToken(String token){
        return Jwts.parserBuilder()
            .setSigningKey(this.publicKey)
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
    

    // private Key generateSecretKey(String secretString){
    //     return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretString));
    // }


}
