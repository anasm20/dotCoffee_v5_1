package com.waff.rest.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.UserDetailsPasswordService;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import com.waff.rest.demo.security.JwtTokenFilter;

import java.util.Arrays;

// Configuration class for defining web security settings and access control.
@Configuration
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true)
public class WebSecurityConfig {

        private static final String[] SWAGGER_WHITELIST = {
                        "/v3/api-docs/**",
                        "/swagger-ui/**",
                        "/swagger-ui.html"
        };

        private static final String[] ADMIN_WHITELIST = {
                        "/user/**",
                        "/swagger-ui/**",
                        "/swagger-ui.html",
                        "/admin/**",
        };

        private static final String[] ANONYMOUS_WHITELIST = {
                        "/register",
                        "/login",
                        "/logout",
        };

        @Autowired
        private JwtTokenFilter jwtTokenFilter;

        @Bean
        public SecurityFilterChain filterChain(HttpSecurity http, DaoAuthenticationProvider daoAuthenticationProvider,
                        StorageConfig storageConfig) throws Exception {

                http.logout()
                                .logoutUrl("/logout")
                                .logoutSuccessUrl("/")
                                .invalidateHttpSession(true)
                                .deleteCookies("JSESSIONID");

                http.csrf().disable();
                // https://docs.spring.io/spring-security/reference/servlet/authentication/passwords/dao-authentication-provider.html
                // authenticationProvider
                http.authenticationProvider(daoAuthenticationProvider);
            // basic authentication
            //http.httpBasic();
            // session management
            http.sessionManagement(management -> management
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    .maximumSessions(1));
                // form login
                //http.formLogin().disable();
                // .defaultSuccessUrl("/swagger-ui/index.html");

                http.cors(cors -> {
                        org.springframework.web.cors.CorsConfigurationSource source = request -> {
                                var config = new org.springframework.web.cors.CorsConfiguration();
                                config.setAllowedOrigins(Arrays.asList("*"));
                                config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
                                config.setAllowedHeaders(Arrays.asList("*"));
                                return config;
                        };
                        cors.configurationSource(source);
                });

                // config access with authorities
                http.authorizeHttpRequests()
                                .requestMatchers(SWAGGER_WHITELIST).hasAnyRole("ADMIN", "USER")
                                .requestMatchers(Arrays.stream(ANONYMOUS_WHITELIST)
                                                .map(AntPathRequestMatcher::antMatcher)
                                                .toArray(RequestMatcher[]::new))
                                .permitAll()
                                
                                // how it checks the authorities, like what does it look for in request
                                // in request you need to include the authorities
                                // like in the header you need to include the authorities
                                // in this way:
                                // fetch('http://localhost:8080/admin/user', {
                                // method: 'GET',
                                // headers: {
                                // 'Content-Type': 'application/json',
                                // 'Authorization': 'Basic ' + btoa('admin:admin')
                                // 
                                .requestMatchers(storageConfig.getLocationPathPattern()).permitAll()
                                .requestMatchers(HttpMethod.POST, "/user").permitAll()
                                .requestMatchers(HttpMethod.GET, "/product/**").permitAll()
                                .requestMatchers(HttpMethod.POST, "/product/search/**").permitAll()
                                .requestMatchers(HttpMethod.GET, "/category/**").permitAll()
                                .requestMatchers(HttpMethod.OPTIONS).permitAll()
                                .requestMatchers("/admin/**").hasRole("ADMIN")
                                .anyRequest().authenticated();

                http.addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);

                return http.build();
        }

        @Bean
        // Creates a DaoAuthenticationProvider (Data Access Object) bean for authentication.
        public DaoAuthenticationProvider daoAuthenticationProvider(UserDetailsService userDetailsService,
                        UserDetailsPasswordService userDetailsPasswordService) {
                DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
                daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
                daoAuthenticationProvider.setUserDetailsService(userDetailsService);
                daoAuthenticationProvider.setUserDetailsPasswordService(userDetailsPasswordService);
                return daoAuthenticationProvider;
        }

        @Bean
        // Creates a BCryptPasswordEncoder bean for password encoding.
        public PasswordEncoder passwordEncoder() {
                return new BCryptPasswordEncoder();
        }

        @Bean
        // Creates a SessionRegistry bean for managing user sessions.
        public SessionRegistry sessionRegistry() {
                return new SessionRegistryImpl();
        }

}
