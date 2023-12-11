package com.example.sprintplannerx.Security;

//import com.example.sprintplannerx.Service.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
@Configuration
public class SecurityConfig {

    //private CustomUserDetailsService customUserDetailsService;

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

//    @Bean
//    public static PasswordEncoder passwordEncoder(){
//        return new BCryptPasswordEncoder();
//    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterSecurity(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeHttpRequests((authorize) ->
                        authorize
                                .requestMatchers(new AntPathRequestMatcher("/register.html")).permitAll()
                                .requestMatchers(new AntPathRequestMatcher("/register")).permitAll()
                                .anyRequest().authenticated()

                ).formLogin(
                        form -> form
                                .loginPage("/login")
                                .loginProcessingUrl("/login")
                                .defaultSuccessUrl("/welcome")
                                .permitAll()
                ).logout(
                        logout -> logout
                                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                                .permitAll()
                );
        return http.build();
    }
}
