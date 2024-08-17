package backendClip.baclend.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Getter
@Builder
@NoArgsConstructor
public class UserUpdateRequest {
  private String name;

  private String password;

  private String email;

  private String role;
}
