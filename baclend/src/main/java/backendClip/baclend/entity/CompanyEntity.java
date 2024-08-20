package backendClip.baclend.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
public class CompanyEntity {

  @Id
  @Column(name = "company_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  private String companyname;

  private String recruitPosition;

  private String reward;

  private String source;

  private String workDetail;

  private String location; // location url


  @OneToMany(mappedBy = "companyEntity")
  private List<ImgEntity> imgs;
}
