package com.Vidstream.vidstream.security;

import com.Vidstream.vidstream.model.MyUserDetails;
import com.Vidstream.vidstream.service.MyUserDetailService;
import com.Vidstream.vidstream.utils.JwtUtil;
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
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private MyUserDetailService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            if (request.getServletPath().equals("/auth/login") ||
                    request.getServletPath().equals("/auth/register") ||
                    request.getServletPath().equals("/video/thumbnail")) {
                logger.info("Skipping token validation for public endpoint: " + request.getServletPath());
                filterChain.doFilter(request, response);
                return;
            }
            // Extract the token from the request
            String token = jwtUtil.extractToken(request);

            // Extract the username from the token
            String username = jwtUtil.extractUsername(token);

            // If the username is extracted and there's no existing authentication in the context
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                // Load the user details from the database
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                // Validate the token
                if (jwtUtil.validateToken(token, (MyUserDetails) userDetails)) {
                    // Create an authentication token
                    UsernamePasswordAuthenticationToken authenticationToken =
                            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                    // Set additional details (e.g., IP address, session ID)
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    // Set the authentication in the SecurityContext
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            }
        } catch (Exception e) {
            // Log the exception and handle it gracefully
            logger.error("\u001B[31mError processing JWT token: " + e.getMessage() + "\u001B[0m");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Unauthorized: Invalid or expired token");
            return; // Stop further processing
        }

        // Continue the filter chain
        filterChain.doFilter(request, response);
    }
}