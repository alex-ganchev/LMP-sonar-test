package digital.razgrad.LMP.config;

import digital.razgrad.LMP.auth.UserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsServiceImpl();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/", "/style.css", "/images/**", "/course/list", "/registration", "/access-denied").permitAll()
                        .requestMatchers( "/profile","/module/list","/lecture/list","/application").authenticated()
                        .requestMatchers( "/application").hasAuthority( "ROLE_STUDENT")
                        .requestMatchers( "/application/**","/course/**","/module/**").hasAnyAuthority( "ROLE_TEACHER", "ROLE_ADMIN")
                        .requestMatchers( "/**").hasAuthority( "ROLE_ADMIN")
                        .anyRequest().authenticated()
                )


                .formLogin((form) -> form
                        .loginPage("/login")
                        .usernameParameter("usernameOrEmail")
                        .permitAll()
                )
                .logout((logout) -> logout
                        .permitAll()
                        .logoutSuccessUrl("/")
                )
                .exceptionHandling().accessDeniedPage("/access-denied");

        return http.build();
    }
}
