package backendClip.baclend.dto.resume;

import backendClip.baclend.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Getter
@Builder
@NoArgsConstructor
public class ResumeRequest {
  private String uploadFile;

  private String description;

  private User user;
}
