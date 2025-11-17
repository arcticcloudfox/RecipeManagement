package com.recipemanagement.security;

import com.recipemanagement.service.RecipeUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final RecipeUserDetailsService recipeUserDetailsService;

    public SecurityConfig(RecipeUserDetailsService recipeUserDetailsService) {
        this.recipeUserDetailsService = recipeUserDetailsService;
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return recipeUserDetailsService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService());
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/recipe/**").authenticated()
                        .requestMatchers("/login", "/signup", "/error", "/css/**").permitAll()
                        .anyRequest().authenticated()
                )
        .formLogin(form -> form
                .loginPage("/login")
                .loginProcessingUrl("/loginUser")
                .defaultSuccessUrl("/recipe", true)
                .permitAll()
                ).logout(logout -> logout
                        .logoutUrl("/logoutUser")
                        .logoutSuccessUrl("/login")
                        .permitAll());

        return http.build();

    }
}
