package com.oneune.laboratory.work.configs.security;

import com.oneune.laboratory.work.configs.properties.JwtAuthorizationProperties;
import com.oneune.laboratory.work.configs.properties.ServerProperties;
import com.oneune.laboratory.work.configs.security.filters.JwtAuthorizationFilter;
import com.oneune.laboratory.work.services.readers.UserReader;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

import java.util.List;

import static org.springframework.http.HttpMethod.*;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;
import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

@Configuration
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@EnableWebSecurity(debug = true)
@EnableMethodSecurity(securedEnabled = true)
@EnableConfigurationProperties(JwtAuthorizationProperties.class)
@Log4j2
public class SecurityConfig {

    UserReader userReader;
    AuthenticationConfiguration authenticationConfiguration;
    JwtAuthorizationFilter jwtAuthorizationFilter;
    BCryptPasswordEncoder passwordEncoder;
    ServerProperties serverProperties;
    WebMvcProperties webMvcProperties;

    @Bean
    MvcRequestMatcher.Builder mvc(HandlerMappingIntrospector introspector) {
        return new MvcRequestMatcher.Builder(introspector);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity, MvcRequestMatcher.Builder mvc) throws Exception {

        String contextPath = this.webMvcProperties.getServlet().getPath();
        String authApiVersion = this.serverProperties.getApi().version().auth();

        final String SIGN_IN = "%s/auth/%s/sign-in".formatted(contextPath, authApiVersion);
        final String LOG_IN = "%s/auth/%s/log-in".formatted(contextPath, authApiVersion);
        final String LOG_UP = "%s/auth/%s/log-up".formatted(contextPath, authApiVersion);
        final String LOG_OUT = "%s/auth/%s/log-out".formatted(contextPath, authApiVersion);

        DefaultSecurityFilterChain customFilterChain = httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(
                        httpRequests -> httpRequests
                                .requestMatchers(antMatcher(("/h2-console/**"))).permitAll()
                                .requestMatchers(mvc.pattern(SIGN_IN), mvc.pattern(LOG_IN), mvc.pattern(LOG_UP)).permitAll()
                                .requestMatchers(antMatcher(SIGN_IN), antMatcher(LOG_IN), antMatcher(LOG_UP)).permitAll()
                                .requestMatchers(mvc.pattern(LOG_OUT)).authenticated()
                                .requestMatchers(antMatcher(SIGN_IN)).authenticated()
//                                .requestMatchers("/**").authenticated()
//                                .requestMatchers("/admin/**").hasAuthority(ADMIN.name()) // refactor with using hasRole and RoleEnum.ADMIN
                                .anyRequest().authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(STATELESS))
                .exceptionHandling(e -> e.authenticationEntryPoint(new HttpStatusEntryPoint(UNAUTHORIZED)))
                .addFilterBefore(this.jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
        log.info("Configured custom security filter chain");
        return customFilterChain;
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(this.passwordEncoder);
        daoAuthenticationProvider.setUserDetailsService(this.userReader);
        log.info("Configured custom dao authentication provider");
        return daoAuthenticationProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager() {
        try {
            return this.authenticationConfiguration.getAuthenticationManager();
        } catch (Exception e) {
            throw new AuthenticationServiceException("Not found authentication manager!");
        }
    }

    private CorsConfiguration getConfiguredCorsConfiguration() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of(CorsConfiguration.ALL));
        configuration.setAllowedMethods(List.of(GET.name(), POST.name(), PUT.name(), DELETE.name(), OPTIONS.name()));
        configuration.setAllowCredentials(true);
        configuration.setAllowedHeaders(List.of(CorsConfiguration.ALL));
        configuration.setExposedHeaders(List.of(CorsConfiguration.ALL));
        return configuration;
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuredCorsConfiguration = this.getConfiguredCorsConfiguration();
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuredCorsConfiguration);
        return source;
    }
}