package backendClip.baclend.repository;

import backendClip.baclend.entity.Resume;
import backendClip.baclend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResumeRepository extends JpaRepository<Resume,Long> {
}
