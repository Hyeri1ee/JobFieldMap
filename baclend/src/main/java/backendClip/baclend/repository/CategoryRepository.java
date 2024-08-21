package backendClip.baclend.repository;

import backendClip.baclend.entity.CategoryEntity;
import backendClip.baclend.entity.CompanyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Integer> {
}
