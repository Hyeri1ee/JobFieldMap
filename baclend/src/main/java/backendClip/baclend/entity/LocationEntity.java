package backendClip.baclend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class LocationEntity {
  @Id
  @Column(name = "COMPANY_ID")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  private double longtitude; //경도
  
  private double latitude; //위도

  @OneToOne
  @JoinColumn(name = "COMPANY_ID")
  private CompanyEntity company;

}
