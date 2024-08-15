package backendClip.baclend.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserUpdateRequest {
  private String name;

  private String password;

  private String email;
}
