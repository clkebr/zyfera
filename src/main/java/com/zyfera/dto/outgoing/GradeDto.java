package com.zyfera.dto.outgoing;

import lombok.*;

@NoArgsConstructor
@Getter
@Setter
@Builder
@AllArgsConstructor
public class GradeDto {

  private String code;
  private Integer value;
}
