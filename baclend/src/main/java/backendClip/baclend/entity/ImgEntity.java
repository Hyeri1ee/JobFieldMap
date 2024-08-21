package backendClip.baclend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
@Entity
@Getter
@Setter
public class ImgEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  private String url;

  @ManyToOne
  @JoinColumn(name = "COMPANY_ID")
  private CompanyEntity company;
}
