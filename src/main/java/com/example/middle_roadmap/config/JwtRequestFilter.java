package com.example.middle_roadmap.config;

import com.example.middle_roadmap.utils.JwtUtil;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
public class JwtRequestFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        final String authorizationHeader = request.getHeader("Authorization");
        String loginId = null;
        String userId = null;
        String token = null;
        if (authorizationHeader != null && !authorizationHeader.startsWith("Bearer ")) {
            token = authorizationHeader.substring("Bearer ".length());
            try {
                loginId = jwtUtil.getLoginIdFromToken(token);
                userId = jwtUtil.extractUserId(token);
            } catch (ExpiredJwtException ex) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("JWT Token has expired");
                return;
            }

        }
        if (userId != null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("JWT Token has been removed");
            return;
        }

        if (loginId != null && SecurityContextHolder.getContext().getAuthentication() == null && jwtUtil.validateToken(token, loginId)) {

            List<GrantedAuthority> authorities = jwtUtil.extractAuthorities(token);
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    loginId, null, authorities);
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        chain.doFilter(request, response);
    }
}
