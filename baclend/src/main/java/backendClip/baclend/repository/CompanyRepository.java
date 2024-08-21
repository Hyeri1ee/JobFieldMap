package backendClip.baclend.repository;

import backendClip.baclend.entity.CompanyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface CompanyRepository extends JpaRepository<CompanyEntity, Integer> {
  CompanyEntity findByRecruitPosition(String position);
}
