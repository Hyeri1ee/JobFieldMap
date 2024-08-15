package backendClip.baclend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "resume")
@Getter
@NoArgsConstructor
@AllArgsConstructor
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

}
