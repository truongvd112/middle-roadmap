package com.example.middle_roadmap.utils;

import com.example.middle_roadmap.entity.Role;
import com.example.middle_roadmap.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParserBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class JwtUtil {
    @Value("${jwt.secret}")
    private String jwtSecret;
    @Value("${jwt.expiration-time:86400000}")
    private long jwtExpirationTime;

    public String createToken(Authentication authentication, User user, String sessionId) {
        // Extract username and roles from the Authentication object
        String username = authentication.getName();

        // Specify token generation date and expiration date
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationTime);

        // Generate JWT token
        return Jwts.builder()
                .setSubject(username)
                .claim("roles", user.getRole().getName())
                .claim("sessionId", sessionId)
                .claim("userId", user.getId())
                .setIssuedAt(now)
                .setExpiration(expiryDate) // Set the expiration date
                .signWith(Keys.hmacShaKeyFor(jwtSecret.getBytes()), SignatureAlgorithm.HS256) // Sign the token with HS512 algorithm and secret key
                .compact(); // Build the token
    }

    public Boolean validateToken(String token, String username) {
        final String usernameFromToken = getLoginIdFromToken(token);
        return username.equals(usernameFromToken) && !isTokenExpired(token);
    }

    public String getLoginIdFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
        JwtParserBuilder parserBuilder = Jwts.parserBuilder();
        parserBuilder.setSigningKey(Keys.hmacShaKeyFor(jwtSecret.getBytes()));
        return parserBuilder.build().parseClaimsJws(token).getBody();
    }

    public List<GrantedAuthority> extractAuthorities(String token) {
        Claims claims = getAllClaimsFromToken(token);
        var role = (String) claims.get("roles");
        var userId = (Integer) claims.get("userId");
        var userName = (String) claims.getSubject();
        return List.of(new SimpleGrantedAuthority(role), new SimpleGrantedAuthority(String.valueOf(userId)), new SimpleGrantedAuthority(userName));
    }

    public Integer extractUserId(String token) {
        Claims claims = getAllClaimsFromToken(token);
        return (Integer) claims.get("userId");
    }
}
