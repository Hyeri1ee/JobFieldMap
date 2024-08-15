package backendClip.baclend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "company")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Company {

  @Id
  @Column(name = "company_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name ="company_name", nullable = false)
  private String name;

  @Column(nullable = false)
  private String description;

  @Column(nullable = false)
  private String location;

  @ManyToOne
  @JoinColumn(name = "category_id")
  private Category category;
}
