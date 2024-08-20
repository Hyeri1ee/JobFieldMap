package backendClip.baclend.dto;

import backendClip.baclend.entity.CompanyEntity;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompanyDTO {
  private String companyname;
  private String recruitPosition;
  private String reward;
  private String source;
  private String workDetail;
  private String location; // location url

  public static CompanyDTO convertEntityToDto(CompanyEntity entity) {
    return CompanyDTO.builder()
            .companyname(entity.getCompanyname())
            .recruitPosition(entity.getRecruitPosition())
            .reward(entity.getReward())
            .source(entity.getSource())
            .workDetail(entity.getWorkDetail())
            .location(entity.getLocation())
            .build();
  }
}
