package backendClip.baclend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class CompanyEntity {

  @Id
  @Column(name = "COMPANY_ID")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  private String companyname;

  private String recruitPosition;

  private String reward;

  private String source;

  @Column(name = "work_detail" ,  length = 2000)
  private String workDetail;



  @OneToMany(mappedBy = "company")
  private List<ImgEntity> imgEntityList;

  @OneToMany(mappedBy = "company")
  private List<CategoryEntity> categoryEntityList;
}
