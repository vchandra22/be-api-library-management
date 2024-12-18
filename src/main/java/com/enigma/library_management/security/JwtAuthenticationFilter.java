package com.enigma.library_management.security;

import com.enigma.library_management.entity.User;
import com.enigma.library_management.service.JwtService;
import com.enigma.library_management.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final static String AUTHORIZATION_HEADER = "Authorization";
    private final JwtService jwtService;
    private final UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String bearerTokenFromAuthHeader = request.getHeader(AUTHORIZATION_HEADER);

            if (bearerTokenFromAuthHeader != null && jwtService.verifyToken(bearerTokenFromAuthHeader)) {
                String userId = jwtService.getUserIdByToken(bearerTokenFromAuthHeader);
                User user = userService.getUserById(userId);

                if (user != null) {
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                    authenticationToken.setDetails(new WebAuthenticationDetails(request));

                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            }
        } catch (Exception e) {
            System.out.println("ERROR JWT AUTHENTICATION FILTER: " + e.getMessage());
            e.printStackTrace();
        }
        filterChain.doFilter(request, response);
    }
}
