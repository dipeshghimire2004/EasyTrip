package org.easytrip.easytripbackend.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.easytrip.easytripbackend.util.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);
    private final JwtUtil jwtUtil;

    public JwtAuthenticationFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
    throws ServletException, IOException {
        String path = request.getRequestURI();
        logger.info("Processing request: {} " , path);
        if (path.startsWith("/api/auth/") || path.startsWith("/swagger-ui/") || path.startsWith("/api-docs")) {
            logger.debug("Skipping JWT filter for: {}", path);
            chain.doFilter(request, response);
            return;
        }

//        HttpServletRequest httpRequest = (HttpServletRequest) request;
//        HttpServletResponse httpResponse = (HttpServletResponse) response;
//        String path = httpRequest.getRequestURI();
//        String path = request.getRequestURI();
//        System.out.println("Filter processing request: " + path); // Debug log
//        if(path.startsWith("/api/auth/") || path.startsWith("/api/auth/login") || path.startsWith("/swagger-ui/") ||path.startsWith("/api-docs")){
//            System.out.println("Skipping filter for: " + path); // Debug log
//            chain.doFilter(request, response);
//            return;
//        }


        String header = request.getHeader("Authorization");
        if(header != null && header.startsWith("Bearer ")) {
            String token = header.substring(7);
            if(jwtUtil.validateToken(token)) {
                String email = jwtUtil.extractEmail(token);
                Set<String> roles = jwtUtil.extractRoles(token);

                if(email !=null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            email,
                            null,
                            roles.stream().map(role-> new SimpleGrantedAuthority("ROLE_"+ role)).collect(Collectors.toList())
                    );
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                    logger.debug("Successfully authenticated user: {}", email);
                }
                else {
                    logger.error("Invalid JWT token for request: {} " , path);
                }
            }
        }
        chain.doFilter(request, response);
    }
}
