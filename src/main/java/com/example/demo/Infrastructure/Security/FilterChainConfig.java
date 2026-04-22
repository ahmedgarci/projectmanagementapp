package com.example.demo.Infrastructure.Security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfigurationSource;

import com.example.demo.Application.Auth.Service.LogoutHandlerImpl;

import lombok.RequiredArgsConstructor;


@Configuration
@RequiredArgsConstructor
public class FilterChainConfig {

    private final RequestFilterConfig requestFilterConfig;
    private final LogoutHandlerImpl logoutHandlerImpl;
    private final CorsConfigurationSource corsConfig;
    
    @Bean
    public SecurityFilterChain filter(HttpSecurity http)throws Exception{
        http
            .cors(c -> c.configurationSource(corsConfig))
            .csrf(csrf -> csrf.disable())
            .exceptionHandling((ex)-> ex.authenticationEntryPoint(new RestAuthenticationEntryPoint()))
            .authorizeHttpRequests((request) -> request.requestMatchers(
                    "/authentication/register",
                    "/authentication/authenticate",
                    "/oauth2/**",             
                    "/login/oauth2/**"    
                                                        ).permitAll()
                                                        .anyRequest()
                                                        .authenticated()
        )
        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .addFilterBefore(requestFilterConfig, UsernamePasswordAuthenticationFilter.class)
        .logout(logout -> logout.logoutUrl("/logout")
                            .addLogoutHandler(logoutHandlerImpl)
        );


        return http.build();
    }
}
