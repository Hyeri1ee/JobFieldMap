package backendClip.baclend.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.bind.annotation.RequestMapping;

@AllArgsConstructor
@Getter
public class UserJoinRequest {

  private String name;

  private String password;

  private String email;
}
