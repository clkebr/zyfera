package com.zyfera.dto.outgoing;

import lombok.*;

@NoArgsConstructor
@Getter
@Setter
@Builder
@AllArgsConstructor
public class GradeDetail {

  private String code;

  private Integer value;
}
