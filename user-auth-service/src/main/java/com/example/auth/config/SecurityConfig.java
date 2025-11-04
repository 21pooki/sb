//package com.example.auth.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
//@Configuration
//public class SecurityConfig {
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//    // IMPORTANT: To keep the application simple and avoid full Spring Security
//    // configuration for API endpoint protection, we are NOT defining a SecurityFilterChain here.
//    // This means all endpoints are publicly accessible, and authentication logic
//    // is handled explicitly within the AuthController/UserService.
//    // For a real-world application, a proper SecurityFilterChain would be essential.
//}














package com.example.auth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager; // You need this import
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // This SecurityFilterChain allows /api/auth/** to be public
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/api/auth/**").permitAll()
                .anyRequest().authenticated()
            )
            .httpBasic(AbstractHttpConfigurer::disable)
            .formLogin(AbstractHttpConfigurer::disable);

        return http.build();
    }

    // This bean satisfies Spring Security's need for a UserDetailsService
    // without using the problematic InMemoryUserDetailsManager auto-configuration.
    // Since we're handling authentication manually, this can be a simple dummy.
    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails dummyUser = User.withUsername("dummy")
                .password(passwordEncoder().encode("dummy")) // Hashing a dummy password
                .roles("USER")
                .build();
        return new InMemoryUserDetailsManager(dummyUser);
    }
}
//**Note:** While Option 2 works, it creates an unnecessary `InMemoryUserDetailsManager` with a dummy user. Option 1 is generally cleaner for your explicit "manual login" use case.
//
//### Recommended Action (Option 1):
//
//1.  **Modify `UserAuthServiceApplication.java`**: Add `exclude = { UserDetailsServiceAutoConfiguration.class }` to the `@SpringBootApplication` annotation.
//2.  **Save all files.**
//3.  **Clean and Rebuild** your project: `mvn clean install`
//4.  **Run** your application: `mvn spring-boot:run`
//
//This should finally resolve the startup error.