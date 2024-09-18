package com.zyfera.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.*;

@NoArgsConstructor
@Getter
@Setter
@ToString
@AllArgsConstructor
@Entity
@Table(name = "grades")
public class Grade extends BaseEntity{

	private String code;

	@Min(0) @Max(100)
	private Integer value;


}
