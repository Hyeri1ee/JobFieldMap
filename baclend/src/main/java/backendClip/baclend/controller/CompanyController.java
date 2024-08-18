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
  @PostMapping("/join/{position}")
  public ResponseEntity joinCompany(@PathVariable("position") String position){//크롤링



  }

  @GetMapping("/get/{position}")
  public ResponseEntity getCompanies(@PathVariable("position") String position){
    List<CompanyDTO> companyDTOList =  companyService.getCompanies(position);
    return ResponseEntity.status(HttpStatus.OK).body(companyDTOList);
  }


}
