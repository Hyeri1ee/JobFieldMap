package backendClip.baclend.dto.resume;

import backendClip.baclend.entity.Resume;
import backendClip.baclend.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Getter
@Builder
@NoArgsConstructor
public class ResumeResponse {
  private String uploadFile;

  private String description;

  private User user;

  public ResumeResponse(Resume resume) {
    this.uploadFile = resume.getUploadFile();
    this.description = resume.getDescription();
    this.user = resume.getUser();
  }
}