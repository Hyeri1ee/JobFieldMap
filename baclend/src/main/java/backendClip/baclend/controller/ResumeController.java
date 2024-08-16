package backendClip.baclend.controller;

import backendClip.baclend.dto.resume.ResumeRequest;
import backendClip.baclend.dto.resume.ResumeResponse;
import backendClip.baclend.dto.user.UserJoinRequest;
import backendClip.baclend.dto.user.UserJoinResponse;
import backendClip.baclend.dto.user.UserUpdateRequest;
import backendClip.baclend.dto.user.UserUpdateResponse;
import backendClip.baclend.entity.Resume;
import backendClip.baclend.entity.User;
import backendClip.baclend.service.ResumeImpl;
import backendClip.baclend.service.UserServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
@RestController
@RequestMapping("/resume")
public class ResumeController {

  private final ResumeImpl resumeService;

  public ResumeController(ResumeImpl resumeService) {
    this.resumeService = resumeService;
  }

  //create
  @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity createResume(@RequestBody ResumeRequest request){
    ResumeResponse resumeResponse = resumeService.create(request);
    Map<String, Object> result = new HashMap<>();
    result.put("response", resumeResponse);
    return ResponseEntity.ok(result);
  }
  //get
  @GetMapping(value = "/get/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity getResume(@PathVariable("id") Long id){
    Resume resume = resumeService.findById(id);
    Map<String, Object> result = new HashMap<>();
    result.put("response", resume);
    return ResponseEntity.ok(result);
  }

  //update info
  @PostMapping(value = "/update/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity updateResume(@PathVariable("id") Long id, @RequestBody ResumeRequest request){
    ResumeResponse resumeResponse = resumeService.update(id, request);
    Map<String, Object> result = new HashMap<>();
    result.put("response", resumeResponse);
    return ResponseEntity.ok(result);
  }

  //withdraw
  @DeleteMapping(value = "/delete/{id}")
  public ResponseEntity removeResume(@PathVariable("id") Long id){
    String message =  resumeService.remove(id);
    return ResponseEntity.status(HttpStatus.OK).body(message);
  }
}
