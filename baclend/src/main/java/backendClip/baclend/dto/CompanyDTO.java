package backendClip.baclend.dto;

import backendClip.baclend.entity.CompanyEntity;
import jakarta.persistence.Column;
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
  private String locationName;

  public static CompanyDTO convertEntityToDto(CompanyEntity entity) {
    return CompanyDTO.builder()
            .companyname(entity.getCompanyname())
            .recruitPosition(entity.getRecruitPosition())
            .reward(entity.getReward())
            .source(entity.getSource())
            .workDetail(entity.getWorkDetail())
            .locationName(entity.getLocationName())
            .build();
  }
}
