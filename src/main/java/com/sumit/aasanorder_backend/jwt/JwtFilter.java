package com.sumit.aasanorder_backend.jwt;

import com.sumit.aasanorder_backend.sevices.CustomUserDetailsService;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
       try{
           String authorizationHeader = request.getHeader("Authorization");
           String username = null;
           String token = null;
           if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
               token = authorizationHeader.substring(7);
               username = jwtUtil.extractUsername(token);
           }
           if(username !=null){
               UserDetails userDetails = userDetailsService.loadUserByUsername(username);
               if(jwtUtil.isTokenValid(token, userDetails)) {
                   UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                   authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                   SecurityContextHolder.getContext().setAuthentication(authentication);
               }
           }
           chain.doFilter(request, response);
       }catch (ExpiredJwtException ex) {
           response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
           response.setContentType("application/json");
           response.getWriter().write("{\"message\": \"Invalid or expired token\"}");
       } catch (Exception ex) {
           response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
           response.setContentType("application/json");
           response.getWriter().write("{\"message\": \"Invalid token\"}");
       }
    }
}

