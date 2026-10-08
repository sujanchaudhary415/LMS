package com.example.demo.security.jwt;

import com.example.demo.security.cookie.CookieService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    public final JwtService jwtService;
    public final CookieService cookieService;
    public final UserDetailsService userDetailsService;

    public static final Logger logger= LoggerFactory.getLogger(JwtAuthFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String jwt= cookieService.getAccessTokenFromCookie(request);
        if(jwt==null){
            filterChain.doFilter(request,response);
            return;
        }
        String username=jwtService.extractUserName(jwt);
        try {
            if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null){
                UserDetails userDetails=userDetailsService.loadUserByUsername(username);
                System.out.println("JWT username: " + username);
                System.out.println("Authorities: " + userDetails.getAuthorities());
                System.out.println("Token valid: " + jwtService.isTokenValid(jwt, userDetails));
                if(jwtService.isTokenValid(jwt,userDetails)){
                    UsernamePasswordAuthenticationToken authentication=new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities()
                    );

                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    logger.debug("User '{}' authenticated successfully",username);
                }
            }

        } catch (UsernameNotFoundException e) {
            logger.debug("User from JWT no longer exists.");

        } catch (Exception e) {
            logger.debug(
                    "JWT authentication failed: {}",
                    e.getMessage()
            );
        }
        filterChain.doFilter(request,response);

    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return request.getServletPath().startsWith("/api/auth/");
    }
}
