package backendClip.baclend.config.auth;

import backendClip.baclend.entity.Role;
import backendClip.baclend.entity.User;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Getter
public class PrincipalDetails implements UserDetails {
  private User user;

  public PrincipalDetails(User user) {
    this.user=user;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() { //유저에게 부여된 권한들 반환
    List<Role> roles = new ArrayList<>(Arrays.asList(Role.ADMIN, Role.USER));
    List<SimpleGrantedAuthority> authorities = new ArrayList<>();
    for (Role role : roles) {
      authorities.add(new SimpleGrantedAuthority(role.getRole()));
    }
    return authorities;
  }

  @Override
  public String getPassword() {
    return user.getPassword();
  }

  @Override
  public String getUsername() {
    return user.getName();
  }

  @Override
  public boolean isAccountNonExpired() { //계정 만료 여부
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  } //계정 잠김 여부

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  } //credentials(password) 만료 여부

  @Override
  public boolean isEnabled() {
    return true;
  } //유저 사용 가능 여부
}
