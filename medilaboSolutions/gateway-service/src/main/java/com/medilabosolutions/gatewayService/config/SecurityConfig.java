package com.medilabosolutions.gatewayService.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

/**
 * The Security configuration of the Gateway
 *
 */
@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    /**
     * The Username.
     */
    @Value("${user.username}")
    String username;

    /**
     * The Password.
     */
    @Value("${user.password}")
    String password;

    /**
     * Password encoder initialization.
     *
     * @return the password encoder
     */
    @Bean
    BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Instantiates a new configured user details service
     *
     * @return the new user in memory who can call the gateway
     */
    @Bean
    public MapReactiveUserDetailsService userDetailsService() {
        UserDetails user = User
                .withUsername(username)
                .password(passwordEncoder().encode(password))
                .build();
        return new MapReactiveUserDetailsService(user);
    }

    /**
     * Configuration of the security filter chain.
     *
     * @param http the http
     * @return the security web filter chain
     */
    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        http.csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(exchanges -> exchanges
                        .pathMatchers("/actuator/**").permitAll()
                        .anyExchange().authenticated()
                )
                .httpBasic(withDefaults());
        return http.build();
    }
}
