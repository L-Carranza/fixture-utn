package com.Fixture.fixtureutn.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean

    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/error").permitAll()
                        .requestMatchers("/api/fixture/data/**").permitAll()
                        // Permitir acceso público a CSS, JS, Imágenes y las vistas públicas
                        .requestMatchers("/", "/home", "/api/fixture/**", "/img/**", "/css/**", "/js/**").permitAll()
                        // Todo lo que empiece con /admin requiere autenticación
                        .requestMatchers("/admin/**").authenticated()
                        .anyRequest().authenticated()
                )
                .formLogin((form) -> form
                        .loginPage("/login") // Página personalizada de login
                        .defaultSuccessUrl("/admin/dashboard", true) // A donde va si el login es correcto
                        .permitAll()
                )
                .logout((logout) -> logout.permitAll());

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        // CREAMOS UN USUARIO EN MEMORIA (Sin base de datos por ahora)
        UserDetails admin = User.withDefaultPasswordEncoder()
                .username("admin")
                .password("1234") // Contraseña
                .roles("ADMIN")
                .build();

        return new InMemoryUserDetailsManager(admin);
    }
}


