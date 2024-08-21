package backendClip.baclend.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LocationDTO {
  private double longtitude; //경도

  private double latitude; //위도
}
