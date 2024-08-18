package backendClip.baclend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class ImgEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  private String url;

  @ManyToOne
  @JoinColumn(name = "company_id")
  private CompanyEntity companyEntity;
}
