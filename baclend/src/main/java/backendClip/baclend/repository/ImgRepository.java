package backendClip.baclend.repository;

import backendClip.baclend.entity.CompanyEntity;
import backendClip.baclend.entity.ImgEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImgRepository extends JpaRepository<ImgEntity, Integer> {
}
