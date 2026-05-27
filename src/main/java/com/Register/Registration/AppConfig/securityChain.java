package com.Register.Registration.AppConfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

import com.Register.Registration.AppService.CostomUserDetail;

@Configuration
@EnableWebSecurity
public class securityChain {

        private final MongoPersistentTokenRepositoryImpl mongoTokenRepository;
        private final CostomUserDetail userDetailsService;

        // Spring autowires your custom MongoDB token repository here
    public securityChain (MongoPersistentTokenRepositoryImpl mongoTokenRepository,  CostomUserDetail userDetailsService) {
        this.mongoTokenRepository = mongoTokenRepository;
        this.userDetailsService = userDetailsService;
    }

        @Bean
        public SecurityFilterChain FilterChain(HttpSecurity http) throws Exception {
                http
                                .csrf(csrf -> csrf.disable())
                                .authorizeHttpRequests(auth -> auth
                                                .requestMatchers("/login", "/register", "/confirm", "/html/*",
                                                                "/css/**")
                                                .permitAll()
                                                .anyRequest().authenticated())
                                .oauth2Login(auth -> auth
                                                .loginPage("/login")
                                                .defaultSuccessUrl("/Dashboard", true)
                                                .permitAll())
                                .rememberMe(remember -> remember
                                                .tokenRepository(mongoTokenRepository) 
                                                .userDetailsService(userDetailsService)
                                                .tokenValiditySeconds(86400 * 30) // 30 days
                                                .rememberMeParameter("remember-me")
                                                .key("mySecretMongoAppKey"))
                                                
                                .logout(logout -> logout
                                                .logoutSuccessUrl("/login?logout")
                                                .invalidateHttpSession(true)
                                                .deleteCookies("JSESSIONID")
                                                .logoutSuccessUrl("/login?logout=true")
                                                .permitAll())
                                .formLogin(form -> form
                                                .loginPage("/login") // Your custom login page
                                                .loginProcessingUrl("/login") // Default POST endpoint - keep this
                                                                              // unless you have strong reason to change
                                                .usernameParameter("userEmail") // default, can customize
                                                .passwordParameter("password") // default
                                                .defaultSuccessUrl("/Dashboard", true) // After successful login
                                                .failureUrl("/login?error=true") // On failure
                                                .permitAll());
                return http.build();

        }

        @Bean
        public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
                return configuration.getAuthenticationManager();
        }

}
