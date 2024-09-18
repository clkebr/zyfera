package com.zyfera.dto;


import jakarta.validation.constraints.NotBlank;
import lombok.*;
import java.util.List;


@NoArgsConstructor
@Getter
@Setter
@Builder
@AllArgsConstructor
public class StudentDto {

	private Long Id;

	@NotBlank
	private String name;

	@NotBlank
	private String surname;

	@NotBlank
	private String stdNumber;

	private List<GradeDto> grades;

}
