package backendClip.baclend.dto.user;

import backendClip.baclend.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Builder
public class UserJoinResponse {
  private String name;

  private String password;

  private String email;

  private String role;

  public UserJoinResponse(User user) {
    this.name = user.getName();
    this.password = user.getPassword();
    this.email = user.getEmail();
    this.role = user.getRole();
  }

}
