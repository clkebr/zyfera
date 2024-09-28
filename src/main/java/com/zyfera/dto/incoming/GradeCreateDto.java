package com.zyfera.dto.incoming;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@NoArgsConstructor
@Getter
@Setter
@Builder
@AllArgsConstructor
public class GradeCreateDto {

  @NotBlank private String code;

  @NotBlank private Integer value;
}
