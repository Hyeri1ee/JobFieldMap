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
  private String detailIntroduction;
  private String workDetail;
  private String requirement;
  private String priority;
  private String benefit;
  private String recruitWay;
  private String stack;
  private String tag;
  private Date dueDate;
  private String location; // location url
  private String website; // website url

  public static CompanyDTO convertEntityToDto(CompanyEntity entity) {
    return CompanyDTO.builder()
            .companyname(entity.getCompanyname())
            .recruitPosition(entity.getRecruitPosition())
            .reward(entity.getReward())
            .source(entity.getSource())
            .detailIntroduction(entity.getDetailIntroduction())
            .workDetail(entity.getWorkDetail())
            .requirement(entity.getRequirement())
            .priority(entity.getPriority())
            .benefit(entity.getBenefit())
            .recruitWay(entity.getRecruitWay())
            .stack(entity.getStack())
            .tag(entity.getTag())
            .dueDate(entity.getDueDate())
            .location(entity.getLocation())
            .website(entity.getWebsite())
            .build();
  }
}
