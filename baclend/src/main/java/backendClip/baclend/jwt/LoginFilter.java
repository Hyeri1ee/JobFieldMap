package backendClip.baclend.jwt;

import backendClip.baclend.dto.CustomUserDetails;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;

public class LoginFilter extends UsernamePasswordAuthenticationFilter {

  private final AuthenticationManager authenticationManager;
  //JwtUtil 주입
  private final JwtUtil jwtUtil;

  public LoginFilter(AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
    this.authenticationManager = authenticationManager;
    this.jwtUtil = jwtUtil;
  }

  @Override
  public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
    if (request.getContentType().equals("application/json")) {
      try {
        StringBuilder sb = new StringBuilder();
        BufferedReader reader = request.getReader();
        String line;
        while ((line = reader.readLine()) != null) {
          sb.append(line);
        }
        String jsonString = sb.toString();

        // JSON 파싱 (Jackson 라이브러리 사용 예시)
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readTree(jsonString);

        String username = jsonNode.get("username").asText();
        String password = jsonNode.get("password").asText();

        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password);
        return authenticationManager.authenticate(authToken);
      } catch (IOException e) {
        throw new AuthenticationServiceException("Failed to parse authentication request body", e);
      }
    } else {
      String username = obtainUsername(request);
      String password = obtainPassword(request);

      if (username == null) {
        username = "";
      }
      if (password == null) {
        password = "";
      }
      username = username.trim();

      UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password);
      return authenticationManager.authenticate(authToken);
    }
  }

  //로그인 성공시 실행하는 메소드 (여기서 JWT를 발급하면 됨)
  @Override
  protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) {
    //UserDetailsS
    CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();

    String username = customUserDetails.getUsername();

    Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
    Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
    GrantedAuthority auth = iterator.next();

    String role = auth.getAuthority();

    String token = jwtUtil.createJwt(username, role, 60*60*10*10*10L); //검증 유효기간 : 1000초 (약 16분)ㄴ

    response.addHeader("Authorization", "Bearer " + token);
  }

  //로그인 실패시 실행하는 메소드
  @Override
  protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) {
    //로그인 실패시 401 반환
    response.setStatus(401);
  }
}
