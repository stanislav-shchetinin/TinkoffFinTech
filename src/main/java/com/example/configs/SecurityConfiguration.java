package com.example.configs;

import com.example.enums.RoleCheck;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.security.web.util.matcher.AndRequestMatcher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RegexRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

import javax.sql.DataSource;

import static org.springframework.security.config.Customizer.withDefaults;
import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(11);
    }

    @Bean
    public JdbcUserDetailsManager users(DataSource dataSource) {
        //password: 12345
        return new JdbcUserDetailsManager(dataSource);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth ->
                        auth
                                .requestMatchers(
                                        new AntPathRequestMatcher("/registration"))
                                .permitAll()
                                .requestMatchers(
                                        new AntPathRequestMatcher("/h2-console/**"))
                                .hasRole(RoleCheck.ADMIN.name())
                                .requestMatchers(
                                        new AntPathRequestMatcher("/swagger-ui/index.html", HttpMethod.GET.name()))
                                .permitAll()
                                .requestMatchers(
                                        new AntPathRequestMatcher("/v3/api-docs", HttpMethod.GET.name()))
                                .permitAll()
                                .requestMatchers(new AntPathRequestMatcher("/v1/current.json", HttpMethod.GET.name() ))
                                .hasAnyRole(RoleCheck.USER.name(), RoleCheck.ADMIN.name())
                                .requestMatchers(new AntPathRequestMatcher("/api/weather/{city}", HttpMethod.GET.name()))
                                .hasAnyRole(RoleCheck.USER.name(), RoleCheck.ADMIN.name())
                                .anyRequest().hasRole(RoleCheck.ADMIN.name())
                )
                .cors(httpSecurityCorsConfigurer -> {})
                .httpBasic(withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .headers(headers -> {
                    headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable);
                });

        return http.build();
    }

}
