package com.zyfera.dto.outgoing;

import java.util.List;
import lombok.*;

@NoArgsConstructor
@Getter
@Setter
@Builder
@AllArgsConstructor
public class StudentDetail {

  private Long id;

  private String name;

  private String surname;

  private String stdNumber;

  private List<GradeDetail> grades;
}
