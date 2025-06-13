package org.example.bazalotow2.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth ->
                        auth
                                .requestMatchers(
                                        "/v3/api-docs/**",
                                        "/swagger-ui/**",
                                        "/swagger-ui.html"
                                ).permitAll()
                                .requestMatchers(HttpMethod.GET, "/airplane").permitAll()
                                .requestMatchers(HttpMethod.GET, "/airplane/**").permitAll()
                                .requestMatchers(HttpMethod.POST, "/airplane").hasAnyAuthority("ADMIN")
                                .requestMatchers(HttpMethod.PUT, "/airplane/**").hasAnyAuthority("ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/airplane/**").hasAnyAuthority("ADMIN")
                                .requestMatchers(HttpMethod.GET, "/city").permitAll()
                                .requestMatchers(HttpMethod.GET, "/city/**").permitAll()
                                .requestMatchers(HttpMethod.POST, "/city").hasAnyAuthority("ADMIN")
                                .requestMatchers(HttpMethod.PUT, "/city/**").hasAnyAuthority("ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/city/**").hasAnyAuthority("ADMIN")
                                .requestMatchers(HttpMethod.GET, "/flight").permitAll()
                                .requestMatchers(HttpMethod.GET, "/flight/**").permitAll()
                                .requestMatchers(HttpMethod.POST, "/flight").hasAnyAuthority("ADMIN")
                                .requestMatchers(HttpMethod.PUT, "/flight/**").hasAnyAuthority("ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/flight/**").hasAnyAuthority("ADMIN")
                                .requestMatchers(HttpMethod.GET, "/flight/*/tickets").hasAnyAuthority("ADMIN")
                                .requestMatchers(HttpMethod.GET, "/ticket").authenticated()
                                .requestMatchers(HttpMethod.GET, "/ticket/**").authenticated()
                                .requestMatchers(HttpMethod.POST, "/ticket").authenticated()
                                .requestMatchers(HttpMethod.PUT, "/ticket/**").authenticated()
                                .requestMatchers(HttpMethod.DELETE, "/ticket/**").authenticated()
                                .requestMatchers(HttpMethod.POST, "/user/register").anonymous()
                                .anyRequest()
                                .authenticated()
                )
                .headers(headers -> headers.frameOptions(frameOptions -> frameOptions.disable()))
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }
}
