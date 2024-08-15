package backendClip.baclend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.sql.ast.tree.predicate.ComparisonPredicate;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "category")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Category {

  @Id
  @Column(name = "category_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "category_name" ,nullable = false)
  private String name;

  @OneToMany(mappedBy = "category")
  private List<Company> companies = new ArrayList<>();

}
