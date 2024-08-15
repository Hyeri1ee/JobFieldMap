package backendClip.baclend.dto.user;

import backendClip.baclend.entity.User;

public class UserJoinResponse {
  private String name;

  private String password;

  private String email;

  public UserJoinResponse(User user) {
    this.name = user.getName();
    this.password = user.getPassword();
    this.email = user.getEmail();
  }
}
