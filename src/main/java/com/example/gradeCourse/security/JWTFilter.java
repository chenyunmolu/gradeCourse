package com.example.gradeCourse.security;

import com.example.gradeCourse.service.UserService;
import io.jsonwebtoken.JwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component // Marks this as a component. Now, Spring Boot will handle the creation and
           // management of the JWTFilter Bean
// and you will be able to inject it in other places of your code
public class JWTFilter extends OncePerRequestFilter {

    private final UserService userDetailsService;
    private final JWTUtil jwtUtil;

    @Autowired
    public JWTFilter(UserService userDetailsService, JWTUtil jwtUtil) {
        this.userDetailsService = userDetailsService;
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {
        // Extracting the "Authorization" header
        String authHeader = request.getHeader("Authorization");

        // 只有请求头中存在Authorization且由Bearer开头，才解析token，否则直接放行到下一个过滤器UsernamePasswordAuthenticationFilter。
        // 因为没有设置UsernamePasswordAuthenticationToken，所以会被下个过滤器拦截。所以无需设置登录接口不走该过滤器。
        // 但访问登录接口，且提供了无效的jwt token会被该过滤器拉住，导致登录失败。
        if (authHeader != null && !authHeader.isBlank() && authHeader.startsWith("Bearer ")) {
            // Extract JWT
            String jwt = authHeader.substring(7);
            if (jwt == null || jwt.isBlank()) {
                // Invalid JWT
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid JWT Token in Bearer Header");
            } else {
                try {
                    // 验证Token并获取从Token中解析的用户名
                    String username = jwtUtil.validateTokenAndRetrieveUsername(jwt);

                    // Fetch User Details
                    UserDetails userDetails = userDetailsService.loadUserByUserid(username);

                    // Create Authentication Token
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username,
                            userDetails.getPassword(), userDetails.getAuthorities());

                    // Setting the authentication on the Security Context using the created token
                    if (SecurityContextHolder.getContext().getAuthentication() == null) {
                        SecurityContextHolder.getContext().setAuthentication(authToken);
                    }
                } catch (JwtException ex) {
                    // Failed to verify JWT
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid JWT Token");
                }
            }
        }

        // Continuing the execution of the filter chain
        filterChain.doFilter(request, response);
    }
}
