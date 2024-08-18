package backendClip.baclend.service;

import backendClip.baclend.dto.CompanyDTO;
import backendClip.baclend.entity.CompanyEntity;
import backendClip.baclend.repository.CompanyRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static backendClip.baclend.dto.CompanyDTO.convertEntityToDto;

@Service
public class CompanyService {
  private final CompanyRepository companyRepository;

  public CompanyService(CompanyRepository companyRepository) {
    this.companyRepository = companyRepository;
  }


  public List<CompanyDTO> getCompanies(String position) {
    List<CompanyEntity> companyEntityList = companyRepository.findByRecruitPosition(position);
    return change(companyEntityList);
  }

  private List<CompanyDTO> change(List<CompanyEntity> companyEntityList) {
    List<CompanyDTO> companyDTOList = new ArrayList<>();
    for ( CompanyEntity entity : companyEntityList){
      CompanyDTO dto = convertEntityToDto(entity);
      companyDTOList.add(dto);
    }
    return companyDTOList;
  }
}
