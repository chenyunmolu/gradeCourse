package com.example.gradeCourse.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity // Enables security for this application
public class SecurityConfiguration {

    private final UserDetailsService uds;
    private final JWTFilter jwtFilter;



    @Autowired
    public SecurityConfiguration(UserDetailsService uds, JWTFilter jwtFilter) {
        this.uds = uds;
        this.jwtFilter = jwtFilter;
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception { // Method to configure your app
                                                                                 // security
        http.csrf().disable() // Disabling csrf
                .httpBasic().disable() // Disabling http basic
                .cors() // Enabling cors
                .and()
                .authorizeHttpRequests() // Authorizing incoming requests
                .antMatchers("/auth/**").permitAll() // Allows auth requests to be made without authentication of
                                                     // any sort
                .antMatchers("/img/**").permitAll()
                .antMatchers("/avatar/**").permitAll()
                .antMatchers("/uploadFile/**").permitAll()
                .antMatchers("/user/**").hasRole("USER") // Allows only users with the "USER" role to make requests
                                                         // to the user routes
                .anyRequest().authenticated() // 正常登录后，请求带token可访问
                // .anyRequest().permitAll() // 暂时放行所有请求
                .and()
                .userDetailsService(uds) // Setting the user details service to the custom implementation
                .exceptionHandling()
                .authenticationEntryPoint(
                        // Rejecting request as unauthorized when entry point is reached
                        // If this point is reached it means that the current request requires
                        // authentication
                        // and no JWT token was found attached to the Authorization header of the
                        // current request.
                        (request, response, authException) -> response.sendError(HttpServletResponse.SC_UNAUTHORIZED,
                                "Unauthorized"))
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS); // Setting Session to be stateless

        // Adding the JWT filter
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
