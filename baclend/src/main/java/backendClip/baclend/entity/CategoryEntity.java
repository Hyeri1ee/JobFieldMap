package backendClip.baclend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class CategoryEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  private String categoryname;

  @ManyToOne
  @JoinColumn(name = "company_id")
  private CompanyEntity companyEntity;
}
