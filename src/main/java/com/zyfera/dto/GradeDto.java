package com.zyfera.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@NoArgsConstructor
@Getter
@Setter
@Builder
@AllArgsConstructor
public class GradeDto {

	@NotBlank
	private String code;

	@NotBlank
	private Integer value;

}
