package backendClip.baclend.service;

import backendClip.baclend.dto.resume.ResumeRequest;
import backendClip.baclend.dto.resume.ResumeResponse;
import backendClip.baclend.dto.user.UserJoinRequest;
import backendClip.baclend.dto.user.UserJoinResponse;
import backendClip.baclend.dto.user.UserUpdateRequest;
import backendClip.baclend.dto.user.UserUpdateResponse;
import backendClip.baclend.entity.Resume;
import backendClip.baclend.entity.User;
import backendClip.baclend.repository.ResumeRepository;
import backendClip.baclend.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class ResumeImpl implements ResumeService{
  private final ResumeRepository resumeRepository;

  public ResumeImpl(ResumeRepository resumeRepository) {
    this.resumeRepository = resumeRepository;
  }

  public ResumeResponse create(ResumeRequest request) {
    Resume resume = Resume.builder()
            .uploadFile(request.getUploadFile())
            .description(request.getDescription())
             //여기에 user정보를 header에서 가져와서 써야함
            .build();
    resumeRepository.save(resume);
    return new ResumeResponse(resume);
  }

  public Resume findById(Long id) {
    Resume resume = resumeRepository.findById(id).orElseThrow( () -> new IllegalArgumentException("resume not found. id= " + id));
    return resume;

  }

  public ResumeResponse update(Long id, ResumeRequest request) {
    Resume resume = resumeRepository.findById(id).orElseThrow( () -> new IllegalArgumentException("resume not found. id= " + id));
    ResumeResponse response = resume.update(request);
    resumeRepository.save(resume);
    return response;
  }

  public String remove(Long id) {
    Resume resume = resumeRepository.findById(id).orElseThrow( () -> new IllegalArgumentException("resume not found. id= " + id));
    resumeRepository.delete(resume);
    return "resume is deleted";
  }
}
