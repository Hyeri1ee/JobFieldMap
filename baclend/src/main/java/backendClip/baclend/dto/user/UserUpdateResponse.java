package backendClip.baclend.dto.user;

import backendClip.baclend.entity.User;
import lombok.Builder;

@Builder
public class UserUpdateResponse {
  private String name;

  private String password;

  private String email;

  public UserUpdateResponse(User user) {
    this.name = user.getName();
    this.password = user.getPassword();
    this.email = user.getEmail();
  }

}
