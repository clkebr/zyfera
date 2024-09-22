package com.zyfera.dto.incoming;

import jakarta.validation.constraints.NotBlank;
import java.util.List;
import lombok.*;

@NoArgsConstructor
@Getter
@Setter
@Builder
@AllArgsConstructor
public class StudentCreateDto {

  @NotBlank private String name;

  @NotBlank private String surname;

  @NotBlank private String stdNumber;

  private List<GradeCreateDto> grades;
}
