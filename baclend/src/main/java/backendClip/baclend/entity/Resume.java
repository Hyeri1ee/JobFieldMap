package backendClip.baclend.entity;

import backendClip.baclend.dto.resume.ResumeRequest;
import backendClip.baclend.dto.resume.ResumeResponse;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "resume")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Resume {
  @Id
  @Column(name = "resume_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String uploadFile;

  @Column(nullable = false)
  private String description;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;

  public ResumeResponse update(ResumeRequest request) {
    this.uploadFile = request.getUploadFile();
    this.description = request.getDescription();
    this.user = request.getUser();

    return ResumeResponse.builder()
            .uploadFile(uploadFile)
            .description(description)
            .user(user)
            .build();
  }
}
