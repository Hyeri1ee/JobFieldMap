package backendClip.baclend.jwt;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {
  @Value("${jwt.secretKey}")
  private String secretKey;
  @Value("${jwt.expiredMs}")
  private  Long expiredMs;

  public String getUserName(String token) {
    return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token)
            .getBody().get("name", String.class);
  }

  public boolean isExpired(String token) {
    System.out.println(secretKey);
    try {
      Claims claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
      Date expirationDate = claims.getExpiration();
      if (expirationDate == null) {
        throw new IllegalArgumentException("Token does not have an expiration date");
      }
      return expirationDate.before(new Date());
    } catch (ExpiredJwtException e) {
      return true;
    } catch (JwtException | IllegalArgumentException e) {
      // 로깅 추가

      throw new IllegalArgumentException("Invalid token: " + e.getMessage(), e);
    }
  }

  public String createJwt(String memberName, Long id) {
    Claims claims = Jwts.claims();
    claims.put("name", memberName);
    System.out.println(secretKey);

    String token = Jwts.builder()
            .setClaims(claims)
            .setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(new Date(System.currentTimeMillis() + expiredMs))
            .signWith(SignatureAlgorithm.HS256, secretKey)
            .compact();
    return token;
  }
}