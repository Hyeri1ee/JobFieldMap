package backendClip.baclend.config;

import backendClip.baclend.jwt.JwtFilter;
import backendClip.baclend.jwt.JwtUtil;
import backendClip.baclend.jwt.LoginFilter;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.tomcat.util.file.ConfigurationSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.io.IOException;
import java.net.URI;
import java.util.Collections;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
  //AuthenticationManager가 인자로 받을 AuthenticationConfiguraion 객체 생성자 주입
  private final AuthenticationConfiguration authenticationConfiguration;
  //JWTUtil 주입
  private final JwtUtil jwtUtil;

  public SecurityConfig(AuthenticationConfiguration authenticationConfiguration, JwtUtil jwtUtil) {
    this.authenticationConfiguration = authenticationConfiguration;
    this.jwtUtil = jwtUtil;
  }

  //AuthenticationManager Bean 등록
  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {

    return configuration.getAuthenticationManager();
  }
  @Bean
  public BCryptPasswordEncoder passwordEncoder(){
    return new BCryptPasswordEncoder();
  }
  @Bean
  public SecurityFilterChain filterChian(HttpSecurity http) throws Exception{

    http
            .cors((corsCustomizer -> corsCustomizer.configurationSource(new CorsConfigurationSource() {

              @Override
              public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {

                CorsConfiguration configuration = new CorsConfiguration();

                configuration.setAllowedOrigins(Collections.singletonList("http://localhost:3000"));
                configuration.setAllowedMethods(Collections.singletonList("*"));
                configuration.setAllowCredentials(true);
                configuration.setAllowedHeaders(Collections.singletonList("*"));
                configuration.setMaxAge(3600L);

                configuration.setExposedHeaders(Collections.singletonList("Authorization"));

                return configuration;
              }
            })));

    //csrf diable
    http
            .csrf((auth)->auth.disable());
    //form 로그인 방식 disable
    http
            .formLogin((auth)-> auth.disable());
    //http basic 인증 방식 disable
    http
            .httpBasic((auth)->auth.disable());
    //경로별 인가 작업
    http
            .authorizeHttpRequests((auth) -> auth
                    .requestMatchers("/login", "/","/join").permitAll()
                    .requestMatchers("/admin").hasRole("ADMIN")
                    .requestMatchers("/company/**").hasAnyRole("ADMIN","USER")
                    .anyRequest().authenticated());
    http
            .addFilterBefore(new JwtFilter(jwtUtil) , LoginFilter.class);
    http //AuthenticationManager()와 JWTUtil 인수 전달
            .addFilterAt(new LoginFilter(authenticationManager(authenticationConfiguration),jwtUtil ), UsernamePasswordAuthenticationFilter.class);
    //세션 설정
    http
            .sessionManagement((session)-> session
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS));
    return http.build();
  }
}
