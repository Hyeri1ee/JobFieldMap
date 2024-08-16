package backendClip.baclend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(authz -> authz
                    .requestMatchers("/user/**").authenticated()
                    .requestMatchers("/resume/**").authenticated()
                    .anyRequest().permitAll()
            )
            .formLogin(form -> form
                    .usernameParameter("name")
                    .passwordParameter("password")
                    .loginPage("/user/login")
                    .defaultSuccessUrl("/security-login")
                    .failureUrl("/security-login/login")
            )
            .logout(logout -> logout
                    .logoutUrl("/user/logout")
                    .invalidateHttpSession(true)
                    .deleteCookies("JSESSIONID")
            );

    return http.build();
  }
}