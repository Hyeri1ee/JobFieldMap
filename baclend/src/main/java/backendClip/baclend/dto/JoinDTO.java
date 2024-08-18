package backendClip.baclend.dto;

import lombok.*;

@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
public class JoinDTO {
  @NonNull
  private String username;
  @NonNull
  private String password;
}
