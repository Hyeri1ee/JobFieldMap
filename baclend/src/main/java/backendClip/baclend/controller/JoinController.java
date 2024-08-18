package backendClip.baclend.controller;

import backendClip.baclend.dto.JoinDTO;
import backendClip.baclend.service.JoinService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.function.EntityResponse;

@Controller
@ResponseBody
public class JoinController {
  private final JoinService joinService;

  public JoinController(JoinService joinService) {
    this.joinService = joinService;
  }

  @PostMapping("/join")
  public ResponseEntity joinProcess(@RequestBody JoinDTO joinDTO){

    String joinedName = joinService.joinProcess(joinDTO);
    return ResponseEntity.status(HttpStatus.OK).body(joinedName);
  }
}
