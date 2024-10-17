package com.dmk.mutbooks.common.security;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer.FrameOptionsConfig;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(PathRequest.toH2Console()).permitAll()
                        .anyRequest().permitAll()
                )
                .formLogin(login -> login
                        .loginPage("/members/login")
                        .defaultSuccessUrl("/")
                )
                .logout(logout -> logout
                        .logoutRequestMatcher(new AntPathRequestMatcher("/members/logout"))
                        .logoutSuccessUrl("/members/login")
                        .invalidateHttpSession(true) // 세션 삭제
                )
                .httpBasic(withDefaults())
                .csrf(csrf -> csrf.ignoringRequestMatchers("/**"))
                .headers(headers -> headers.frameOptions(FrameOptionsConfig::sameOrigin))
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
