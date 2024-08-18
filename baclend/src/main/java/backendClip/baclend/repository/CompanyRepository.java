package backendClip.baclend.repository;

import backendClip.baclend.entity.CompanyEntity;
import backendClip.baclend.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CompanyRepository extends JpaRepository<CompanyEntity, Integer> {
  List<CompanyEntity> findByRecruitPosition(String position);
}
