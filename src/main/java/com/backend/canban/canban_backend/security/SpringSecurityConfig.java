package com.backend.canban.canban_backend.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.cors.reactive.CorsWebFilter;

import java.util.Arrays;


@Configuration // данный класс будет считан как конфиг для spring контейнера
@EnableWebSecurity // включает механизм защиты адресов, которые настраиваются в SecurityFilterChain

public class SpringSecurityConfig {

    @Value("${client.url}")
    private String clientURL; // клиентский URL


    // создается спец. бин, который отвечает за настройки запросов по http
    // (метод вызывается автоматически) Spring контейнером
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        // все сетевые настройки
        http.authorizeRequests()
                .anyRequest().permitAll() // разрешаем все запросы, не требуем токенов и пр.
                .and()

                .csrf().disable() // отключаем встроенную защиту от CSRF атак
                .cors();// разрешает выполнять OPTIONS запросы от клиента (preflight запросы) без авторизации

                // добавляем новые настройки, не связанные с предыдущими

        http.requiresChannel().anyRequest().requiresSecure(); // обязательное исп. HTTPS для всех запросах
        return http.build();
    }

    // все эти настройки обязательны для корректного сохранения куков в браузере
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowCredentials(true);
        configuration.setAllowedOrigins(Arrays.asList(clientURL));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("*"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    /*
    @Bean
    public CorsWebFilter corsFilter() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowCredentials(true);
        configuration.setAllowedOrigins(Arrays.asList(clientURL));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("*"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return new CorsWebFilter(source);
    }
    */

}
