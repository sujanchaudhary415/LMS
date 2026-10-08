package com.example.demo.security.jwt;
import com.example.demo.security.services.impl.UserDetailsImpl;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import javax.crypto.SecretKey;
import java.util.Date;
import java.util.List;

@Component
public class JwtService {

    private final JwtProperties jwtProperties;
    private final SecretKey key;


    public JwtService(JwtProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
        byte[] keyBytes= Decoders.BASE64.decode(jwtProperties.getSecret());
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateAccessToken(UserDetailsImpl userDetails){
        List<String>roles=userDetails.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        return Jwts.builder()
                .subject(userDetails.getEmail())
                .claim("roles",roles)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis()+jwtProperties.getAccessTokenExpiration()))
                .signWith(key)
                .compact();
    }

    public String extractUserName(String token){
        try {
            return parseJwtClaims(token).getPayload().getSubject();
        }
        catch(JwtException | IllegalArgumentException e){
            return null;
        }
    }

    public boolean isTokenValid(String token, UserDetails userDetails){
        try {
            String username=extractUserName(token);
            return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
        }
        catch (JwtException | IllegalArgumentException e){
            return false;
        }
    }

    private Jws<Claims> parseJwtClaims(String token){
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token);
    }

    private boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token){
        return parseJwtClaims(token).getPayload().getExpiration();
    }
}
