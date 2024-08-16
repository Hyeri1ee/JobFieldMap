package backendClip.baclend.dto.user;

import backendClip.baclend.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Builder
public class UserUpdateResponse {
  private String name;

  private String password;

  private String email;

}
