package backendClip.baclend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
@Entity
@Getter
@Setter
public class CategoryEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  private String categoryname;

  @ManyToOne
  @JoinColumn(name = "COMPANY_ID")
  private CompanyEntity company;
}
