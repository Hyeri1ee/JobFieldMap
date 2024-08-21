package backendClip.baclend.controller;

import backendClip.baclend.dto.CompanyDTO;
import backendClip.baclend.service.CompanyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/company")
public class CompanyController {
  private final CompanyService companyService;

  public CompanyController(CompanyService companyService) {
    this.companyService = companyService;
  }

  //크롤링 구현후->REACT화면 만들기
  @PostMapping("/join")
  public ResponseEntity joinCompany(){//크롤링
    List<CompanyDTO> companyDTOList =  companyService.crollingAndSave();
    return ResponseEntity.status(HttpStatus.OK).body(companyDTOList);

  }

  @GetMapping("/get/{position}")
  public ResponseEntity getCompanies(@PathVariable("position") String position){
    CompanyDTO companyDTO =  companyService.getCompany(position);
    return ResponseEntity.status(HttpStatus.OK).body(companyDTO);
  }

}
